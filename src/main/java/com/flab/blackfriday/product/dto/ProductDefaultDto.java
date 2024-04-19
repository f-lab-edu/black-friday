package com.flab.blackfriday.product.dto;

import com.flab.blackfriday.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.product.dto
 * fileName       : ProductDefaultDto
 * author         : rhkdg
 * date           : 2024-04-18
 * description    : 상품 검색 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-18        rhkdg       최초 생성
 */
@Getter
@Setter
public class ProductDefaultDto extends BaseDto {

    /**상품번호*/
    private String pNum = "";

    /**상품제목*/
    private String pTitle = "";

}
