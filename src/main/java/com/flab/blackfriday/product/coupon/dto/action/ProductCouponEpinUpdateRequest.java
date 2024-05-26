package com.flab.blackfriday.product.coupon.dto.action;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.product.coupon.dto.action
 * fileName       : ProductCouponEpinUpdateRequest
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
public class ProductCouponEpinUpdateRequest {

    private String couponNum = "";

    private long idx = 0L;

    private String id = "";

}
