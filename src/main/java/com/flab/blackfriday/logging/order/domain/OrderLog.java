package com.flab.blackfriday.logging.order.domain;

import com.flab.blackfriday.logging.order.OrderLogStatus;
import com.flab.blackfriday.logging.order.dto.OrderLogDto;
import com.flab.blackfriday.modules.order.dto.OrderStatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.logging.order
 * fileName       : OrderLog
 * author         : rhkdg
 * date           : 2024-06-20
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-20        rhkdg       최초 생성
 */
@Entity
@Table(name="order_log")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment("부모 idx")
    private Long parmentIdx;

    @Comment("주문 정보")
    private String orderData;
    
    @Comment("주문 상태")
    private OrderStatusType orderStatus;
    
    @Comment("등록일자")
    private LocalDateTime createDate;
    
    @Comment("수정일자")
    private LocalDateTime modifyDate;

    public OrderLog(OrderLogDto dto){
        this.idx = dto.getIdx();
        this.parmentIdx = dto.getParentIdx();
        this.orderData = dto.getOrderData();
        this.orderStatus = OrderStatusType.valueOf(dto.getOrderStatus());
    }

}
