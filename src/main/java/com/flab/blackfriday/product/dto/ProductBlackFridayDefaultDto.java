package com.flab.blackfriday.product.dto;

import com.flab.blackfriday.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.product.dto
 * fileName       : ProductBlackFridayDefaultDto
 * author         : GAMJA
 * date           : 2024/04/21
 * description    : 블랙 프라이데이 검색용 Dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/21        GAMJA       최초 생성
 */
@Getter
@Setter
public class ProductBlackFridayDefaultDto extends BaseDto {

    /**상품 번호*/
    private String pNum = "";

    /**사용여부*/
    private String useYn = "";

}
