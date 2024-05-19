package com.flab.blackfriday.order.dto.action;

import lombok.*;

/**
 * packageName    : com.flab.blackfriday.order.dto.action
 * fileName       : OrderItemCreateRequest
 * author         : rhkdg
 * date           : 2024-05-11
 * description    : 상품 옵션 정보 등록
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-11        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItemRequest {

    /**상품번호*/
    private Long pitmIdx = 0L;

    /**상품 개수*/
    private int pCnt = 0;

    /**가격*/
    private int price = 0;

}
