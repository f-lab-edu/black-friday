package com.flab.blackfriday.modules.order.dto;

import com.flab.blackfriday.modules.product.dto.ProductItemSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.order.dto
 * fileName       : OrderItemResponse
 * author         : GAMJA
 * date           : 2024/05/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/10        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    /**일련번호*/
    private Long idx = 0L;

    /**주문일련 번호*/
    private Long oIdx = 0L;

    private ProductItemSummaryResponse productItemSummaryResponse;

    /**상품 개수*/
    private int pCnt = 0;

    /**가격*/
    private int price = 0;

    /**등록일자*/
    private LocalDateTime createDate;

    /**수정일자*/
    private LocalDateTime modifyDate;


}
