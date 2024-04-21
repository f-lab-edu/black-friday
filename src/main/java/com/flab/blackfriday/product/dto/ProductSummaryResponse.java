package com.flab.blackfriday.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.product.dto
 * fileName       : ProductSummaryInfo
 * author         : GAMJA
 * date           : 2024/04/21
 * description    : 상품 summary response
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/21        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSummaryResponse {

    /**상품 번호*/
    private String pNum = "";

    /**상품 제목*/
    private String pTitle = "";

    /**상품 카테고리 번호*/
    private String categCd = "";

    /**상품 카테고리 명*/
    private String categNm = "";

    /**할인율*/
    private int sale = 0;

    public ProductSummaryResponse(String pNum, String pTitle, String categCd, String categNm){
        this.pNum = pNum;
        this.pTitle = pTitle;
        this.categCd = categCd;
        this.categNm = categNm;
    }

}
