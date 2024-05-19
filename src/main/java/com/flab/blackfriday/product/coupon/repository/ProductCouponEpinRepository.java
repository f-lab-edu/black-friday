package com.flab.blackfriday.product.coupon.repository;

import com.flab.blackfriday.product.coupon.domain.ProductCouponEpin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
public interface ProductCouponEpinRepository extends JpaRepository<ProductCouponEpin, String> {

    /**
     * 상품 번호 생성
     * @return
     */
    @Query(value=" select CONCAT('COUPON',CONVERT(IFNULL(MAX(p_num),concat(FORMATDATETIME(NOW(),'yyyyMMdd'),'00')),int)+1) as pNum  FROM product "+
            "	where p_num like concat('COUPON',FORMATDATETIME(NOW(),'yyyyMMdd'),'%')", nativeQuery = true)
    String selectProductCouponNumMax();

}
