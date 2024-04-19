package com.flab.blackfriday.category.dto;

import com.flab.blackfriday.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.category.dto
 * fileName       : CategoryDefaultDto
 * author         : GAMJA
 * date           : 2024/04/19
 * description    : 카테고리 검색 Dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/19        GAMJA       최초 생성
 */
@Getter
@Setter
public class CategoryDefaultDto extends BaseDto {

    /**카테고리 코드*/
    private String categCd = "";

    /**부모 코드*/
    private String parentCd = "";

    /**사용여부*/
    private String useYn = "";

}
