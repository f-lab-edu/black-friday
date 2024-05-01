package com.flab.blackfriday.category.controller;

import com.flab.blackfriday.category.dto.CategoryDefaultDto;
import com.flab.blackfriday.category.dto.CategoryDto;
import com.flab.blackfriday.category.dto.CategoryListAndTotCntResponse;
import com.flab.blackfriday.category.dto.CategoryRequest;
import com.flab.blackfriday.category.service.CategoryService;
import com.flab.blackfriday.common.controller.BaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * packageName    : com.flab.blackfriday.category.controller
 * fileName       : CategoryController
 * author         : GAMJA
 * date           : 2024/04/19
 * description    : 관리자 카테고리 관리 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/19        GAMJA       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class CategoryController extends BaseController {

    private final CategoryService categoryService;

    /**
     * 카테고리 목록
     * @param searchDto
     * @return
     * @throws Exception
     */
    @GetMapping(MGN_URL+"/category/list")
    public CategoryListAndTotCntResponse selectCategoryList(CategoryDefaultDto searchDto) throws Exception {
        long totCnt = categoryService.selectCategoryTotalCount(searchDto);
        List<CategoryDto> resultList = categoryService.selectCategoryList(searchDto);
        return CategoryListAndTotCntResponse.of(resultList,totCnt);
    }

    /**
     * 상세 조회
     * @param categCd
     * @return CategoryDto
     * @throws Exception
     */
    @GetMapping(MGN_URL+"/category/view/{categCd}")
    public CategoryDto selectCategory(@PathVariable String categCd) throws Exception {

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategCd(categCd);
        categoryDto = categoryService.selectCategory(categoryDto);
        return categoryDto;
    }

    /**
     * 카테고리 등록
     * @param categoryRequest
     * @return
     * @throws Exception
     */
    @PostMapping(MGN_URL+"/category/ins")
    public ResponseEntity<?> insertCategory(@RequestBody @Valid CategoryRequest categoryRequest) throws Exception {

        try{
            categoryService.saveCategory(CategoryDto.requestOf(categoryRequest));
        }catch (Exception e) {
            logger.error("insert category error : {}",e.getMessage());
            modelMap.put("msg","카테고리 등록시 오류가 발생했습니다.");
            return new ResponseEntity<>(modelMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        modelMap.put("msg","등록 되었습니다.");
        return new ResponseEntity<>(modelMap,HttpStatus.OK);
    }

    /**
     * 카테고리 수정(업데이트)
     * @param categoryRequest
     * @return
     * @throws Exception
     */
    @PutMapping(MGN_URL+"/category/upd")
    public ResponseEntity<?> updateCategory(@RequestBody @Valid CategoryRequest categoryRequest) throws Exception {

        try{
            CategoryDto prevDto = categoryService.selectCategory(CategoryDto.requestOf(categoryRequest));
            if(prevDto == null){
                modelMap.put("msg","잘못된 접근입니다.");
                return new ResponseEntity<>(modelMap,HttpStatus.BAD_REQUEST);
            }
            CategoryDto categoryDto = CategoryDto.requestOf(categoryRequest);
            categoryDto.setCreateDate(prevDto.getCreateDate());
            categoryService.saveCategory(categoryDto);
        }catch (Exception e) {
            logger.error("category update error : {}",e.getMessage());
            modelMap.put("msg","카테고리 수정시 오류가 발생했습니다.");
            return new ResponseEntity<>(modelMap,HttpStatus.UNPROCESSABLE_ENTITY);
        }

        modelMap.put("msg","수정 되었습니다.");
        return new ResponseEntity<>(modelMap,HttpStatus.OK);
    }

    /**
     * 카테고리 삭제
     * @param categCd
     * @return
     * @throws Exception
     */
    @DeleteMapping(MGN_URL+"/category/del")
    public ResponseEntity<?> deleteCategory(@RequestParam("categCd") String categCd) throws Exception {

        try{
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategCd(categCd);
            categoryService.deleteCategory(categoryDto);
        }catch (Exception e) {
            logger.error("delete category error : {}",e.getMessage());
            modelMap.put("msg","카테고리 삭제시 오류가 발생하였습니다.");
            return new ResponseEntity<>(modelMap,HttpStatus.UNPROCESSABLE_ENTITY);
        }

        modelMap.put("msg","삭제 되었습니다.");
        return new ResponseEntity<>(modelMap,HttpStatus.OK);
    }
}
