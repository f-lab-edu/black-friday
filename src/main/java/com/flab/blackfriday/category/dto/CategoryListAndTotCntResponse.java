package com.flab.blackfriday.category.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : com.flab.blackfriday.category.dto
 * fileName       : CategoryListAndTotCntResponse
 * author         : rhkdg
 * date           : 2024-05-01
 * description    : 카테고리 목록과 전체 개수를 가져오는 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-01        rhkdg       최초 생성
 */
@Getter
@Setter
@Builder
public class CategoryListAndTotCntResponse {

    private List<CategoryDto> categoryDtoList;

    private long totCnt;

    public static CategoryListAndTotCntResponse of(List<CategoryDto> list, long totCnt) {
        return new CategoryListAndTotCntResponseBuilder()
                .categoryDtoList(list)
                .totCnt(totCnt)
                .build();
    }

}
