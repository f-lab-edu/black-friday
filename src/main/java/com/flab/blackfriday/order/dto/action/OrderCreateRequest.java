package com.flab.blackfriday.order.dto.action;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : com.flab.blackfriday.order.dto.action
 * fileName       : OrderCreateRequest
 * author         : GAMJA
 * date           : 2024/05/10
 * description    : 주문 정보 등록
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/10        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderCreateRequest {

    /**상품번호*/
    private String pNum = "";

    /**상품 옵션 정보*/
    private List<OrderItemRequest> itemList = new ArrayList<>(); 

}
