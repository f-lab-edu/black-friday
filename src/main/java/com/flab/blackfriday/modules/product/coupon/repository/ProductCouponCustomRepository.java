package com.flab.blackfriday.modules.product.coupon.repository;

import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponDefaultDto;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponDto;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponSummaryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * packageName    : com.flab.blackfriday.product.coupon.repository
 * fileName       : ProductCouponCustomRepository
 * author         : GAMJA
 * date           : 2024/05/15
 * description    : 쿠폰 처리 custom repository
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/15        GAMJA       최초 생성
 */
public interface ProductCouponCustomRepository {

    /**
     * 쿠폰 세팅 목록 list( 페이징 o)
     * @param searchDto
     * @return
     * @throws Exception
     */
    Page<ProductCouponSummaryResponse> selectProductCouponPageList(ProductCouponDefaultDto searchDto) throws Exception;

    /**
     * 쿠폰 세팅 목록 list( 페이징 x )
     * @param searchDto
     * @return
     * @throws Exception
     */
    List<ProductCouponSummaryResponse> selectProductCouponList(ProductCouponDefaultDto searchDto) throws Exception;

    /**
     *
     * @param dto
     * @return
     * @throws Exception
     */
    ProductCouponDto selectProductCoupon(ProductCouponDto dto) throws Exception;

}
