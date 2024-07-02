package com.flab.blackfriday.modules.product.coupon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.product.coupon.dto
 * fileName       : ProductCouponEpinWithInfoResponse
 * author         : GAMJA
 * date           : 2024/05/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/26        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductCouponEpinWithInfoResponse {

    private String couponNum = "";

    private String id = "";

    private ProductCouponSummaryResponse productCouponSummaryResponse = new ProductCouponSummaryResponse();

    public ProductCouponEpinWithInfoResponse (String couponNum , String id,
                                              long idx, String title, String startDate ,String endDate) {
        this.couponNum = couponNum;
        this.id = id;
        this.getProductCouponSummaryResponse().setIdx(idx);
        this.getProductCouponSummaryResponse().setTitle(title);
        this.getProductCouponSummaryResponse().setStartDate(startDate);
        this.getProductCouponSummaryResponse().setEndDate(endDate);

    }

}
