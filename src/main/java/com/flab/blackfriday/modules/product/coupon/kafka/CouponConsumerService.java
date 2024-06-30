package com.flab.blackfriday.modules.product.coupon.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.blackfriday.common.exception.CommonNotUseException;
import com.flab.blackfriday.common.exception.NoExistAuthException;
import com.flab.blackfriday.modules.product.coupon.dto.CouponUseStatus;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponDto;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponEpinDto;
import com.flab.blackfriday.modules.product.coupon.dto.action.ProductCouponEpinRequest;
import com.flab.blackfriday.modules.product.coupon.service.ProductCouponService;
import com.flab.blackfriday.modules.product.coupon.util.CouponRandomUtil;
import com.flab.blackfriday.modules.product.domain.Product;
import io.netty.util.concurrent.CompleteFuture;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * packageName    : com.flab.blackfriday.modules.product.coupon.kafka
 * fileName       : CouponKafkaConsumerService
 * author         : rhkdg
 * date           : 2024-06-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-11        rhkdg       최초 생성
 */
@Service
@RequiredArgsConstructor
public class CouponConsumerService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductCouponService productCouponService;

    @Getter
    private final Map<String,String> couponMap = new ConcurrentHashMap<>();

    private final Map<String, CompletableFuture<String>> couponConsumerMap = new ConcurrentHashMap<>();

    /**
     * 쿠폰 생성 전달
     * @param message
     * @throws Exception
     */
    private Map<String,String> couponCreate(String message) throws Exception {

        Map<String,String> sendMap = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();

        ProductCouponEpinRequest productCouponEpinRequest = mapper.readValue(message, ProductCouponEpinRequest.class);
        ProductCouponEpinDto epinDto = ProductCouponEpinDto.createOf(productCouponEpinRequest);

        ProductCouponDto configDto = new ProductCouponDto();
        configDto.setIdx(epinDto.getIdx());
        configDto= productCouponService.selectProductCoupon(configDto);
        if(configDto == null ){
           sendMap.put("status", HttpStatus.UNPROCESSABLE_ENTITY.name());
           sendMap.put("id",epinDto.getId());
           sendMap.put("idx",epinDto.getIdx()+"");
           sendMap.put("message","잘못된 접근입니다.");
           return sendMap;
        }

        if(configDto.getCouponCnt() == 0){
            sendMap.put("status", HttpStatus.UNPROCESSABLE_ENTITY.name());
            sendMap.put("id",epinDto.getId());
            sendMap.put("idx",epinDto.getIdx()+"");
            sendMap.put("message","해당 상품에 대한 쿠폰을 생성할 수 없습니다.");
            return sendMap;
        }

        String couponNum = CouponRandomUtil.createUuid(10)+epinDto.getIdx();

        //중복 쿠폰 검색을 필터를 위한 용도
//        Map<String,String> existCouponsMap = new HashMap<>();
//        //쿠폰생성
//        while (true) {
//            try {
//                couponNum = CouponRandomUtil.randomMix(10);
//                //중복된 쿠폰 정보가 이미 존재할 경우에 대한 체크
//                if (existCouponsMap.get(couponNum) == null) {
//                    epinDto.setCouponNum(couponNum);
//                    ProductCouponEpinDto existCheck = productCouponService.selectProductCouponEpin(epinDto);
//                    if (existCheck == null) {
//                        break;
//                    } else {
//                        existCouponsMap.put(couponNum, couponNum);
//                    }
//                }
//            }catch (Exception e){
//                sendMap.put("status", HttpStatus.UNPROCESSABLE_ENTITY.name());
//                sendMap.put("message",e.getMessage());
//                return sendMap;
//            }
//        }

        sendMap.put("status", HttpStatus.OK.name());
        sendMap.put("id",epinDto.getId());
        sendMap.put("idx",epinDto.getIdx()+"");
        sendMap.put("couponNum",couponNum);
        sendMap.put("useType",epinDto.getUseType());
        return sendMap;
    }

    /**
     * 쿠폰 생성 결과 처리
     * @param message
     * @throws Exception
     */
    @KafkaListener(topics = "coupon_create", groupId = "couponGroup")
    public void couponInsert(String message) throws Exception {

        Map<String,String> map = couponCreate(message);
        ProductCouponEpinDto dto = new ProductCouponEpinDto();
        dto.setId(map.get("id"));
        dto.setIdx(Long.parseLong(map.get("idx")));

        if(map.get("status").equals(HttpStatus.OK.name())) {
            dto.setCouponNum(map.get("couponNum"));
            dto.setUseType(map.get("useType"));
            dto.setUseStatus(CouponUseStatus.NONE.name());
            productCouponService.insertProductCouponEpin(dto);
        }

        CompletableFuture<String> future = couponConsumerMap.get(dto.getId()+"_"+dto.getIdx());
        if (future != null) {
            if(!map.get("status").equals(HttpStatus.OK.name())) {
                logger.error("### non-blocking future exception . ### ");
                future.completeExceptionally(new Exception(map.get("message")));
            }else {
                future.complete(dto.getCouponNum());
                logger.error("### non-blocking 실행. ### {} :", dto.getCouponNum());
                couponConsumerMap.remove(dto.getId() + "_" + dto.getIdx());
            }
        } else {
            logger.error("### non-blocking future 없음 . ### ");
        }
    }

    /**
     * 쿠폰 발급 초기값 생성(비동기 )
     * @param code
     * @return
     */
    public CompletableFuture<String> waitForString(String code){
        CompletableFuture<String> future = new CompletableFuture<>();
        couponConsumerMap.put(code,future);
        return future;
    }

}
