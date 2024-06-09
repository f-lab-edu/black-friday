package com.flab.blackfriday.modules.order.dto;

import com.flab.blackfriday.modules.product.dto.ProductSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.order.dto
 * fileName       : OrderSummaryResponse
 * author         : GAMJA
 * date           : 2024/05/10
 * description    : 주문 요약 response
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/10        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummaryResponse {

    /**일련번호*/
    private Long idx = 0L;

    /**
     * 상품 기본 정보
     */
    private ProductSummaryResponse productSummaryResponse;

    /**주문상태*/
    private String orderStatus = "";

    /**결제상태*/
    private String payStatus = "";

    /**구매 가격*/
    private int price = 0;

}
