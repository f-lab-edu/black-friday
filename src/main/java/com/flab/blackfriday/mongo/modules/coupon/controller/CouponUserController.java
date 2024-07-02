package com.flab.blackfriday.mongo.modules.coupon.controller;

import com.flab.blackfriday.auth.member.dto.MemberSession;
import com.flab.blackfriday.common.controller.BaseModuleController;
import com.flab.blackfriday.common.exception.NoExistAuthException;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponEpinDto;
import com.flab.blackfriday.modules.product.coupon.dto.action.ProductCouponEpinRequest;
import com.flab.blackfriday.mongo.modules.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.flab.blackfriday.mongo.modules.coupon.controller
 * fileName       : CouponUserController
 * author         : rhkdg
 * date           : 2024-06-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-27        rhkdg       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class CouponUserController extends BaseModuleController {

    private final CouponService couponMongoService;

    private final MemberSession memberSession;

    /**
     * 쿠폰 생성
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping(value=API_URL+"/mongo/product/coupon/epin/create")
    public ResponseEntity<?> createProductCouponEpin(@RequestParam("id") String id)  throws Exception {

        if(!memberSession.isAuthenticated()){
            logger.error("### 인증되지 않은 접근. ### ");
            throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.", HttpStatus.UNAUTHORIZED.name());
        }

        String couponNum = couponMongoService.updateCouponEpinToMember(id);

        return ResponseEntity.ok(couponNum);
    }
}
