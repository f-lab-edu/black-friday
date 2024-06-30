package com.flab.blackfriday.modules.product.coupon.service;

import com.flab.blackfriday.common.exception.CommonNotUseException;
import com.flab.blackfriday.common.exception.NoExistAuthException;
import com.flab.blackfriday.modules.product.coupon.domain.ProductCouponEpin;
import com.flab.blackfriday.modules.product.coupon.dto.CouponUseStatus;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponEpinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.flab.blackfriday.modules.product.coupon.service
 * fileName       : ProductCouponAppService
 * author         : rhkdg
 * date           : 2024-06-22
 * description    : 쿠폰 사용 및 발급 service
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-22        rhkdg       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true,value = "mysqlTx")
public class ProductCouponAppService {

    private final ProductCouponService productCouponService;


    /**
     * 쿠폰 발급
     * @param productCouponEpinDto
     * @throws Exception
     */
    @Transactional(value = "mysqlTx")
    public String updateProductCouponEpinIssueToMember(ProductCouponEpinDto productCouponEpinDto) throws Exception {

        String couponNum = productCouponService.selectProductCouponEpinIssueToMember(productCouponEpinDto.getIdx());
        if(couponNum == null){
            throw new NoExistAuthException("더이상 발급이 불가능합니다.");
        }
        productCouponEpinDto.setCouponNum(couponNum);
        productCouponEpinDto.setUseStatus(CouponUseStatus.NONE.name());
        int result = productCouponService.updateProductCouponEpinCompareAndSet(productCouponEpinDto);
        if(result == 0){
            throw new CommonNotUseException("다시 쿠폰 요청해주시기 바랍니다.");
        }

        return couponNum;
    }
}
