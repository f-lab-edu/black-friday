package com.flab.blackfriday.modules.product.coupon.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.blackfriday.modules.product.coupon.dto.CouponUseStatus;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponEpinDto;
import com.flab.blackfriday.modules.product.coupon.service.ProductCouponService;
import io.netty.util.concurrent.CompleteFuture;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
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
     * 쿠폰 생성 결과 처리
     * @param message
     * @throws Exception
     */
    @KafkaListener(topics = "coupon_create", groupId = "couponGroup")
//    @Async
    public void couponInsert(String message) throws Exception {

        logger.debug("### receive coupon num : "+message);

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Map<String, String>> typeReference = new TypeReference<Map<String, String>>() {};
        Map<String, String> map = mapper.readValue(message, typeReference);

        ProductCouponEpinDto dto = new ProductCouponEpinDto();
        dto.setId(map.get("id"));
        dto.setIdx(Long.parseLong(map.get("idx")));
        dto.setCouponNum(map.get("couponNum"));
        dto.setUseType(map.get("useType"));
        dto.setUseStatus(CouponUseStatus.NONE.name());
        productCouponService.insertProductCouponEpin(dto);

        CompletableFuture<String> future = couponConsumerMap.get(dto.getId()+"_"+dto.getIdx()+"_"+dto.getCouponNum());
        if(future != null){
            future.complete(dto.getCouponNum());
            logger.error("### 비동기 실행. ### {} :", dto.getCouponNum());
            couponConsumerMap.remove(dto.getId()+"_"+dto.getIdx()+"_"+dto.getCouponNum());
        }else {
            logger.error("### 비동기 future 없음 . ### ");
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
