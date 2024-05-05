package com.flab.blackfriday.category.dto;

import com.flab.blackfriday.category.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.category.dto
 * fileName       : CategoryDto
 * author         : rhkdg
 * date           : 2024-04-17
 * description    : 카테고리 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-17        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    /**카테고리 코드*/
    private String categCd = "";

    /**부모 코드*/
    private String parentCd = "";
    
    /**카테고리 명*/
    private String categNm = "";

    /**정렬*/
    private int ord = 0;

    /**사용유무*/
    private String useYn = "";
    
    /**등록일자*/
    private LocalDateTime createDate;
    
    /**수정일자*/
    private LocalDateTime modifyDate;

    /**
     * dto -> entity
     * @return
     */
    public Category toEntity(){
        return Category.builder().dto(this).build();
    }

    public CategoryDto(Category entity){
        this.categCd = entity.getCategCd();
        this.parentCd = entity.getParentCd();
        this.categNm = entity.getCategNm();
        this.ord = entity.getOrd();
        this.useYn = entity.getUseYn();
        this.createDate = entity.getCreateDate();
        this.modifyDate = entity.getModifyDate();
    }

    public static CategoryDto requestOf(CategoryRequest request){
        CategoryDto dto = new CategoryDto();
        dto.setCategCd(request.getCategCd());
        dto.setParentCd(request.getParentCd());
        dto.setCategNm(request.getCategNm());
        dto.setOrd(request.getOrd());
        dto.setUseYn(request.getUseYn());
        return dto;
    }
    
}
