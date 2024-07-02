package com.flab.blackfriday.modules.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.product.dto
 * fileName       : ProductItemSummaryResponse
 * author         : GAMJA
 * date           : 2024/05/10
 * description    : 상품 옵션 정보
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/10        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemSummaryResponse {

    /**일련번호*/
    private Long idx = 0L;

    /**상품 이름*/
    private String pItmName = "";

    /**상품 옵션 가격*/
    private int pItmPrice = 0;

    /**상품 옵션 개수*/
    private int pItmCnt = 0;

    /**상품 비고*/
    private String pItmRemark = "";

}
