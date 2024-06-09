package com.flab.blackfriday.modules.order.dto;

import com.flab.blackfriday.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.order.dto
 * fileName       : OrderDefaultDto
 * author         : rhkdg
 * date           : 2024-04-18
 * description    : 주문 검색 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-18        rhkdg       최초 생성
 */
@Getter
@Setter
public class OrderDefaultDto extends BaseDto {

    /**아이디*/
    private String id = "";

    /**상품번호*/
    private String pNum = "";

}
