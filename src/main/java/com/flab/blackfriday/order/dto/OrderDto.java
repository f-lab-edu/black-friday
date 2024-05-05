package com.flab.blackfriday.order.dto;

import com.flab.blackfriday.order.domain.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    /**
     * dto -> entity
     * @return
     */
    public Order toEntity(){
        return Order.builder().dto(this).build();
    }
    
}
