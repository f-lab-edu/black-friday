package com.flab.blackfriday.product.coupon.dto;

import com.flab.blackfriday.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.product.coupon.dto
 * fileName       : ProductCouponDefaultDto
 * author         : GAMJA
 * date           : 2024/05/15
 * description    : 쿠폰 설정 검색 Dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/15        GAMJA       최초 생성
 */
@Getter
@Setter
public class ProductCouponDefaultDto extends BaseDto {

    private String date = "";

    private String startDate = "";

    private String endDate = "";

}
