package com.flab.blackfriday.product.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.product.coupon.dto
 * fileName       : ProductCouponDto
 * author         : GAMJA
 * date           : 2024/05/15
 * description    : 쿠폰 세팅 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/15        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCouponDto {

    private long idx = 0L;

    private String title = "";

    private String remark = "";

    private String startDate = "";

    private String endDate = "";

    private String categCdGroup = "";

    private String productGroup = "";

    private int couponCnt = 0;

    private int sale = 0;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

}
