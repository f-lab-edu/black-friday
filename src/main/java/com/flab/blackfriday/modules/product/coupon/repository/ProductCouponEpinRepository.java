package com.flab.blackfriday.modules.product.coupon.repository;

import com.flab.blackfriday.modules.product.coupon.domain.ProductCouponEpin;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponEpinDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    /**
     * 쿠폰 번호 발급 하기 (미리 생성되어있는 쿠폰에 대한 발급입니다.)
     * @param idx
     * @return
     */
    @Query(value = "select coupon_num from product_coupon_epin where idx=:idx and member_id is null order by create_date ASC limit 1", nativeQuery = true)
    String selectProductEpinFirstCouponNum(@Param("idx") long idx);

    @Query(value = "select count(*) from product_coupon_epin where idx=:idx ",nativeQuery = true)
    long selectPRoductEpinCntFromConfig(@Param("idx") long idx);


    @Modifying
    @Query(value = "UPDATE product_coupon_epin set member_id =:#{#epinDto.id} where coupon_num=:#{#epinDto.couponNum} and member_id is null", nativeQuery = true)
    int updateProductCouponEpinCompareAndSet(@Param("epinDto") ProductCouponEpinDto epinDto);

}
