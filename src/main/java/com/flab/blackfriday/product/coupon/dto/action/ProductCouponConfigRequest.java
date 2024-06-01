package com.flab.blackfriday.product.coupon.dto.action;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.product.coupon.dto.action
 * fileName       : ProductCouponRequest
 * author         : GAMJA
 * date           : 2024/05/15
 * description    : 쿠폰 세팅 create request
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/15        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductCouponConfigRequest {

    private String title = "";

    private String remark = "";

    private String startDate = "";

    private String endDate = "";

    private String categCdGroup = "";

    private String productGroup = "";

    private int couponCnt = 0;

    private int sale = 0;

}
