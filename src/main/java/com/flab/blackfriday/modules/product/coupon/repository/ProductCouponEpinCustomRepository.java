package com.flab.blackfriday.modules.product.coupon.repository;

import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponDefaultDto;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponEpinWithInfoResponse;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * packageName    : com.flab.blackfriday.product.coupon.repository
 * fileName       : ProductCouponEpinCustomRepository
 * author         : GAMJA
 * date           : 2024/05/16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/16        GAMJA       최초 생성
 */
public interface ProductCouponEpinCustomRepository {

    Page<ProductCouponEpinWithInfoResponse> selectProductCouponEpinPageList(ProductCouponDefaultDto searchDto) throws Exception;

    List<ProductCouponEpinWithInfoResponse> selectProductCouponEpinList(ProductCouponDefaultDto searchDto) throws Exception;

    long selectProductCouponEpinExistCnt(String couponNum) throws Exception;

}
