package com.flab.blackfriday.product.coupon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.product.coupon.dto
 * fileName       : ProductCouponLogDto
 * author         : GAMJA
 * date           : 2024/05/15
 * description    : 쿠폰 로그 Dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/15        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductCouponLogDto {

    private long idx = 0L;

    private String couponNum = "";

    private long couponIdx = 0L;

    private String id = "";

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

}
