package com.flab.blackfriday.product.coupon.dto;

import lombok.Getter;

/**
 * packageName    : com.flab.blackfriday.product.coupon.dto
 * fileName       : CouponCreateType
 * author         : GAMJA
 * date           : 2024/05/26
 * description    : 쿠폰 전달 방식 (바로 생성 하는 것이냐, 회원이 등록하여 사용하냐의 차이)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/26        GAMJA       최초 생성
 */
@Getter
public enum CouponUseType {

    NOW("바로적용"),
    SLOW("회원적용");

    private String display = "";

    CouponUseType(String display){
        this.display = display;
    }
}
