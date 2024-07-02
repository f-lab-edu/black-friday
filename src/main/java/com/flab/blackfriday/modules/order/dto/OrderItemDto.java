package com.flab.blackfriday.modules.order.dto;

import com.flab.blackfriday.modules.order.domain.OrderItem;
import com.flab.blackfriday.modules.order.dto.action.OrderItemRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

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
@ToString
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

    /**
     * dto -> entity
     * @return
     */
    public OrderItem toEntity() {
        return OrderItem.builder().dto(this).build();
    }

    public static OrderItemDto responseOf(OrderItemResponse response){
        OrderItemDto itemDto = new OrderItemDto();
        itemDto.setIdx(response.getIdx());
        itemDto.setOIdx(response.getOIdx());
        itemDto.setPitmIdx(response.getProductItemSummaryResponse().getIdx());
        itemDto.setPCnt(response.getPCnt());
        itemDto.setPrice(response.getPrice());
        return itemDto;
    }

    /**
     * 등록 처리
     * @param orderItemRequest
     * @return
     */
    public static OrderItemDto orderOf(OrderItemRequest orderItemRequest){
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setPitmIdx(orderItemRequest.getPitmIdx());
        orderItemDto.setPCnt(orderItemRequest.getPCnt());
        orderItemDto.setPrice(orderItemRequest.getPrice());
        return orderItemDto;
    }
}
