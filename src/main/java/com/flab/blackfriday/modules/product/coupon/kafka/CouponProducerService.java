package com.flab.blackfriday.modules.product.coupon.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.blackfriday.common.exception.CommonNotUseException;
import com.flab.blackfriday.common.exception.NoExistAuthException;
import com.flab.blackfriday.modules.product.coupon.domain.ProductCouponConfig;
import com.flab.blackfriday.modules.product.coupon.domain.ProductCouponEpin;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponDto;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponEpinDto;
import com.flab.blackfriday.modules.product.coupon.service.ProductCouponService;
import com.flab.blackfriday.modules.product.coupon.util.CouponRandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * packageName    : com.flab.blackfriday.modules.product.coupon.kafka
 * fileName       : CouponProducer
 * author         : rhkdg
 * date           : 2024-06-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-09        rhkdg       최초 생성
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CouponProducerService {

    private static final String TOPIC = "coupon_create";

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ProductCouponService productCouponService;

    private final Lock lock = new ReentrantLock();

    /**
     * 쿠폰 생성 전달
     * @param epinDto
     * @throws Exception
     */
    public Map<String,String> couponCreate(ProductCouponEpinDto epinDto) throws Exception {

        ProductCouponDto configDto = new ProductCouponDto();
        configDto.setIdx(epinDto.getIdx());
        configDto= productCouponService.selectProductCoupon(configDto);
        if(configDto == null ){
            throw new NoExistAuthException("잘못된 접근입니다.");
        }

        if(configDto.getCouponCnt() == 0){
            throw new CommonNotUseException("해당 상품에 대한 쿠폰을 생성할 수 없습니다.");
        }

        String couponNum = "";

        //쿠폰 생성에 대한 락 설정
        lock.lock();
        try {
            //쿠폰생성
            while (true) {
                couponNum = CouponRandomUtil.randomMix(10);
                ProductCouponEpinDto existCheck = productCouponService.selectProductCouponEpin(epinDto);
                if (existCheck == null)
                    break;
            }
        }finally {
            lock.unlock();
        }

        Map<String,String> sendMap = new HashMap<>();
        sendMap.put("id",epinDto.getId());
        sendMap.put("idx",epinDto.getIdx()+"");
        sendMap.put("couponNum",couponNum);
        sendMap.put("useType",epinDto.getUseType());
        return sendMap;
    }

    public void sendMessage(Map<String,String> sendMap) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(sendMap));
    }

}
