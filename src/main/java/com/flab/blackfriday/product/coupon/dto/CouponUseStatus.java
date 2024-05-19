package com.flab.blackfriday.product.coupon.dto;

import lombok.Getter;

/**
 * packageName    : com.flab.blackfriday.product.coupon.dto
 * fileName       : CouponUseStatus
 * author         : GAMJA
 * date           : 2024/05/15
 * description    : 쿠폰 사용 상태 enum
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/15        GAMJA       최초 생성
 */
@Getter
public enum CouponUseStatus {

    NONE("미사용"),
    OK("사용");

    private String display = "";

    CouponUseStatus(String display) {
        this.display = display;
    }
}
