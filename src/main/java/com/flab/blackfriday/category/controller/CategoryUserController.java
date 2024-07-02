package com.flab.blackfriday.category.controller;

import com.flab.blackfriday.category.dto.CategoryDefaultDto;
import com.flab.blackfriday.category.dto.CategoryDto;
import com.flab.blackfriday.category.dto.CategorySummaryResponse;
import com.flab.blackfriday.category.repository.CategoryRepository;
import com.flab.blackfriday.category.service.CategoryService;
import com.flab.blackfriday.common.controller.BaseModuleController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * packageName    : com.flab.blackfriday.category.controller
 * fileName       : CategoryUserController
 * author         : GAMJA
 * date           : 2024/04/19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/19        GAMJA       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class CategoryUserController extends BaseModuleController {

    private final CategoryService categoryService;

    /**
     * 카테고리 조회(캐싱 적용)
     * @param searchDto
     * @return
     * @throws Exception
     */
    @GetMapping(API_URL+"/category/list")
    public List<CategorySummaryResponse> selectCategoryList(CategoryDefaultDto searchDto) throws Exception {

        searchDto.setUseYn("Y");
        List<CategoryDto> list = categoryService.selectCategoryCacheList(searchDto);
        return list.stream()
                .map(x-> CategorySummaryResponse.builder().dto(x).build()).toList();
    }

    /**
     * 카테고리 상세조회
     * @param categCd
     * @return
     * @throws Exception
     */
    @GetMapping(API_URL+"/category/view/{categCd}")
    public CategoryDto viewCategory(@PathVariable String categCd) throws Exception {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategCd(categCd);
        categoryDto = categoryService.selectCategory(categoryDto);
        return categoryDto;
    }
}
