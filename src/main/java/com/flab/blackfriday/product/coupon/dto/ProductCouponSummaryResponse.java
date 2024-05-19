package com.flab.blackfriday.product.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.product.coupon.dto
 * fileName       : ProductCouponSummaryResponse
 * author         : GAMJA
 * date           : 2024/05/15
 * description    : 쿠폰 설정 summary response
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/15        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCouponSummaryResponse {

    private long idx = 0L;

    private String title = "";

    private String startDate = "";

    private String endDate = "";

    private int couponCnt = 0;

    private int sale = 0;

}
