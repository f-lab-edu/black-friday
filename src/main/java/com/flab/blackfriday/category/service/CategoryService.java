package com.flab.blackfriday.category.service;

import com.flab.blackfriday.category.domain.Category;
import com.flab.blackfriday.category.dto.CategoryDefaultDto;
import com.flab.blackfriday.category.dto.CategoryDto;
import com.flab.blackfriday.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : com.flab.blackfriday.category.service
 * fileName       : CategoryService
 * author         : GAMJA
 * date           : 2024/04/19
 * description    : 카테고리 service
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/19        GAMJA       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;


    /**
     * 카테고리 목록 (no paging)
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<CategoryDto> selectCategoryList(CategoryDefaultDto searchDto) throws Exception {
        return categoryRepository.selectCategoryList(searchDto);
    }

    /**
     * 카테고리 전체 개수
     * @param searchDto
     * @return
     * @throws Exception
     */
    public long selectCategoryTotalCount(CategoryDefaultDto searchDto) throws Exception {
        return categoryRepository.selectCategoryTotalCount(searchDto);
    }

    /**
     * 카테고리 상세 조회
     * @param dto
     * @return
     * @throws Exception
     */
    public CategoryDto selectCategory(CategoryDto dto) throws Exception {
        Category category = categoryRepository.findById(dto.getCategCd()).orElse(null);
        return category != null ? new CategoryDto(category) : null;
    }


    /**
     * 카테고리 등록,수정
     * @param dto
     * @throws Exception
     */
    @Transactional
    public void saveCategory(CategoryDto dto) throws Exception {
        if(dto.getParentCd().isEmpty()){
            dto.setParentCd("_TOP"); //부모 코드라는 의미 (상위 default 값)
        }
        categoryRepository.save(dto.toEntity());
    }

    @Transactional
    public void deleteCategory(CategoryDto dto) throws Exception {
        //만약 parentCd가 _TOP일 경우 삭제시 아래 관련 카테고리는 다 삭제되어야 함
        if(dto.getParentCd().equals("_TOP")){
            List<Category> list = categoryRepository.findByParentCdAndCategCd(dto.getParentCd(),dto.getCategCd());
            if(list.size() > 0){
                for(Category c : list){
                    if(c.getId() != null) {
                        categoryRepository.deleteById(c.getId());
                    }
                }
            }
        }
        categoryRepository.deleteById(dto.getCategCd());
    }
}
