package com.flab.blackfriday.category.repository;

import com.flab.blackfriday.category.dto.CategoryDefaultDto;
import com.flab.blackfriday.category.dto.CategoryDto;

import java.util.List;

/**
 * packageName    : com.flab.blackfriday.category.repository
 * fileName       : CategoryCustomRepository
 * author         : GAMJA
 * date           : 2024/04/19
 * description    : 카테고리 service interface
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/19        GAMJA       최초 생성
 */
public interface CategoryCustomRepository {

    /**
     * 카테고리 목록 조회
     *
     * @param searchDto
     * @return
     */
    List<CategoryDto> selectCategoryList(CategoryDefaultDto searchDto);


    /**
     * 카테고리 개수 조회
     * @param searchDto
     * @return
     */
    long selectCategoryTotalCount(CategoryDefaultDto searchDto);
}
