package com.flab.blackfriday.product.dto;

import lombok.*;

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

    public ProductSummaryResponse(String pNum, String pTitle, String categCd, String categNm, int sale){
        this.pNum = pNum;
        this.pTitle = pTitle;
        this.categCd = categCd;
        this.categNm = categNm;
        this.sale = sale;
    }

    public ProductSummaryResponse(String pNum, String pTitle, String categCd, String categNm){
        this.pNum = pNum;
        this.pTitle = pTitle;
        this.categCd = categCd;
        this.categNm = categNm;
    }

    public static ProductSummaryResponse fromDto(ProductDto dto){
        ProductSummaryResponse response = new ProductSummaryResponse();
        response.setPNum(dto.getPNum());
        response.setPTitle(dto.getPTitle());
        response.setCategCd(dto.getCategCd());
        response.setCategNm(dto.getCategNm());
        return response;
    }

}
