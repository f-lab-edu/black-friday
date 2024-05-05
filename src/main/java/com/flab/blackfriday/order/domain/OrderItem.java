package com.flab.blackfriday.order.domain;

import com.flab.blackfriday.order.dto.OrderItemDto;
import com.flab.blackfriday.product.domain.ProductItem;
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
 * fileName       : OrderItem
 * author         : rhkdg
 * date           : 2024-04-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-18        rhkdg       최초 생성
 */
@Entity
@Table(name="order_item")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "o_idx",nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name= "pitm_idx",nullable = false)
    private ProductItem productItem;

    @Comment("상품 개수")
    private int pCnt;

    @Comment("상품 가격")
    private int price;

    @Comment("등록일자")
    @CreatedDate
    private LocalDateTime createDate;

    @Comment("수정일자")
    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Builder
    public OrderItem (OrderItemDto dto ){
        if(dto.getIdx() > 0){
            this.idx = dto.getIdx();
        }
        this.order = new Order();
        this.order.addIdx(dto.getIdx());
        this.productItem = new ProductItem();
        this.productItem.addIdx(dto.getPitmIdx());
        this.pCnt = dto.getPCnt();
        this.price = dto.getPrice();
        this.createDate = dto.getCreateDate();
        this.modifyDate = dto.getModifyDate();
    }

    public void addIdx(Long idx){
        this.idx = idx;
    }

}
