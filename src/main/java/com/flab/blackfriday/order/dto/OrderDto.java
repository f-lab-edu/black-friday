package com.flab.blackfriday.order.dto;

import com.flab.blackfriday.order.domain.Order;
import com.flab.blackfriday.order.dto.action.OrderCreateRequest;
import com.flab.blackfriday.order.dto.action.OrderItemRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : com.flab.blackfriday.order.dto
 * fileName       : OrderDto
 * author         : rhkdg
 * date           : 2024-04-18
 * description    : 주문 정보 Dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-18        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderDto {
    
    /**일련번호*/
    private Long idx = 0L;
    
    /**상품번호*/
    private String pNum = "";
    
    /**회원 아이디*/
    private String id = "";
    
    /**주문상태*/
    private String orderStatus = "";
    
    /**결제상태*/
    private String payStatus = "";
    
    /**구매 가격*/
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
    public Order toEntity(){
        return Order.builder().dto(this).build();
    }

    public OrderDto (Order entity) {
        this.idx = entity.getIdx();
        this.pNum = entity.getProduct().getPNum();
        this.id = entity.getMember().getId();
        this.orderStatus = entity.getOrderStatusType().name();
        this.payStatus = entity.getPayStatusType().name();
        this.price = entity.getPrice();
        this.createDate = entity.getCreateDate();
        this.modifyDate = entity.getModifyDate();
    }

    /**
     * 주문 처리를 위한 request
     * @param orderCreateRequest
     * @return
     */
    public static OrderDto orderOf(OrderCreateRequest orderCreateRequest) {
        OrderDto orderDto = new OrderDto();
        orderDto.setPNum(orderCreateRequest.getPNum());
        int amount = 0;
        for(OrderItemRequest item : orderCreateRequest.getItemList()){
            amount += item.getPrice();
            orderDto.getItemList().add(OrderItemDto.orderOf(item));
        }
        orderDto.setPrice(amount);
        return orderDto;
    }
    
}
