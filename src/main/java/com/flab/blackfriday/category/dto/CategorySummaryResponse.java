package com.flab.blackfriday.category.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.category.dto
 * fileName       : CategorySummaryResponse
 * author         : rhkdg
 * date           : 2024-04-30
 * description    : 카테고리 요약 dto response
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-30        rhkdg       최초 생성
 */
@Getter
@Setter
public class CategorySummaryResponse {
    
    /**카테고리 번호*/
    private String categCd = "";
    
    /**카테고리 명*/
    private String categNm = "";

    @Builder
    public CategorySummaryResponse (CategoryDto dto) {
        this.categCd = dto.getCategCd();
        this.categNm = dto.getCategNm();
    }
    
}
