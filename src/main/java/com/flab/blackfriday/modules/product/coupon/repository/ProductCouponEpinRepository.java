package com.flab.blackfriday.modules.product.coupon.repository;

import com.flab.blackfriday.modules.product.coupon.domain.ProductCouponEpin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.flab.blackfriday.product.coupon.repository
 * fileName       : ProductCouponEpinRepository
 * author         : GAMJA
 * date           : 2024/05/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/15        GAMJA       최초 생성
 */
public interface ProductCouponEpinRepository extends JpaRepository<ProductCouponEpin, String> , ProductCouponEpinCustomRepository{
}
