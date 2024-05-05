package com.flab.blackfriday.order.dto;

import com.flab.blackfriday.order.domain.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : com.flab.blackfriday.order.dto
 * fileName       : OrderItemDto
 * author         : rhkdg
 * date           : 2024-04-18
 * description    : 주문 아이템 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-18        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderItemDto {

    /**일련번호*/
    private Long idx = 0L;

    /**주문일련 번호*/
    private Long oIdx = 0L;

    /**상품번호*/
    private Long pitmIdx = 0L;

    /**상품 개수*/
    private int pCnt = 0;

    /**가격*/
    private int price = 0;

    /**등록일자*/
    private LocalDateTime createDate;

    /**수정일자*/
    private LocalDateTime modifyDate;

    private List<OrderItemDto> itemList = new ArrayList<>();

    /**
     * dto -> entity
     * @return
     */
    public OrderItem toEntity() {
        return OrderItem.builder().dto(this).build();
    }
}
