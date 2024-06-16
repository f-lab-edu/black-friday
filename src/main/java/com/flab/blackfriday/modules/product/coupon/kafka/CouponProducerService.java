package com.flab.blackfriday.modules.product.coupon.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.blackfriday.common.exception.CommonNotUseException;
import com.flab.blackfriday.common.exception.NoExistAuthException;
import com.flab.blackfriday.modules.product.coupon.domain.ProductCouponConfig;
import com.flab.blackfriday.modules.product.coupon.domain.ProductCouponEpin;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponDto;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponEpinDto;
import com.flab.blackfriday.modules.product.coupon.dto.action.ProductCouponEpinRequest;
import com.flab.blackfriday.modules.product.coupon.service.ProductCouponService;
import com.flab.blackfriday.modules.product.coupon.util.CouponRandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
@Service
@RequiredArgsConstructor
public class CouponProducerService {

    private static final String TOPIC = "coupon_create";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(ProductCouponEpinRequest productCouponEpinRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(productCouponEpinRequest));
    }

}
