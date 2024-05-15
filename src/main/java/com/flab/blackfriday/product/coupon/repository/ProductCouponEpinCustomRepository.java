package com.flab.blackfriday.product.coupon.repository;

import com.flab.blackfriday.product.coupon.domain.ProductCouponEpin;
import com.flab.blackfriday.product.coupon.dto.ProductCouponDefaultDto;
import com.flab.blackfriday.product.coupon.dto.ProductCouponEpinDto;
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

    Page<ProductCouponEpinDto> selectProductCouponEpinPageList(ProductCouponDefaultDto searchDto) throws Exception;

    List<ProductCouponEpinDto> selectProductCouponEpinList(ProductCouponDefaultDto searchDto) throws Exception;

    ProductCouponEpinDto selectProductCouponEpin(ProductCouponEpinDto dto) throws Exception;

}
