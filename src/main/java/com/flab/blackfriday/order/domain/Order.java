package com.flab.blackfriday.order.domain;

import com.flab.blackfriday.auth.member.domain.Member;
import com.flab.blackfriday.order.dto.OrderDto;
import com.flab.blackfriday.order.dto.OrderStatusType;
import com.flab.blackfriday.order.dto.PayStatusType;
import com.flab.blackfriday.product.domain.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.order.domain
 * fileName       : Order
 * author         : rhkdg
 * date           : 2024-04-17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-17        rhkdg       최초 생성
 */
@Entity
@Table(name="orders")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "p_num",nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id",nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Comment("구매상태")
    private OrderStatusType orderStatusType;

    @Enumerated(EnumType.STRING)
    @Comment("결제상태")
    private PayStatusType payStatusType;

    @Comment("구매 금액")
    private int price;

    @Comment("등록일자")
    @CreatedDate
    private LocalDateTime createDate;

    @Comment("수정일자")
    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Builder
    public Order(OrderDto dto) {
        if(dto.getIdx() > 0){
            this.idx = dto.getIdx();
        }
        this.product = new Product();
        this.product.addPnum(dto.getPNum());
        this.member = new Member();
        this.member.addId(dto.getId());
        this.orderStatusType = OrderStatusType.valueOf(dto.getOrderStatus());
        this.payStatusType = PayStatusType.valueOf(dto.getPayStatus());
        this.price = dto.getPrice();
        this.createDate = dto.getCreateDate();
        this.modifyDate = dto.getModifyDate();
    }

    public void addIdx(Long idx){
        this.idx = idx;
    }

}
