package com.flab.blackfriday.mongo.modules.coupon.controller;

import com.flab.blackfriday.common.controller.BaseController;
import com.flab.blackfriday.common.response.CommonResponse;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponDto;
import com.flab.blackfriday.mongo.modules.coupon.document.CouponConfig;
import com.flab.blackfriday.mongo.modules.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.flab.blackfriday.mongo.modules.coupon.controller
 * fileName       : CouponController
 * author         : rhkdg
 * date           : 2024-06-27
 * description    : 쿠폰 발급
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-27        rhkdg       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class CouponController extends BaseController {

    private final CouponService couponMongoService;

    /**
     * 관리자 쿠폰 발급
     * @param config
     * @return
     * @throws Exception
     */
    @PostMapping(MGN_URL+"/mongo/coupon/epin/create")
    public ResponseEntity<?> createCoupon(CouponConfig config) throws Exception {
        couponMongoService.insertCouponEpinCreate(config);
        return ResponseEntity.ok(new CommonResponse("등록 되었습니다.", null));
    }
}
