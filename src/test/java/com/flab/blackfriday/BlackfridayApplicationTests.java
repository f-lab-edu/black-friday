package com.flab.blackfriday;

import com.flab.blackfriday.modules.order.service.OrderService;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponDto;
import com.flab.blackfriday.modules.product.coupon.service.ProductCouponService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"local"})
class BlackfridayApplicationTests {

    @Autowired
    ProductCouponService productCouponService;

    @DisplayName("cms system log AOP 예외처리 테스트")
    @Test
    void test() {

        try {
            //given
            ProductCouponDto productCouponDto = new ProductCouponDto();
            productCouponDto.setIdx(1);

            //when
            productCouponService.insertProductCouponEpin2(productCouponDto);

            //then

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
