package com.flab.blackfriday.product.domain;

import com.flab.blackfriday.product.dto.ProductBlackFridayDto;
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
 * packageName    : com.flab.blackfriday.product.domain
 * fileName       : ProductBlackFriday
 * author         : rhkdg
 * date           : 2024-04-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-18        rhkdg       최초 생성
 */
@Entity
@Table(name="product_black_friday")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductBlackFriday {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @OneToOne
    @JoinColumn(name="p_num",nullable = false)
    private Product product;

    @Comment("할인(%)")
    private int sale;

    @Comment("사용유무")
    @Column(columnDefinition = "char",length = 1)
    private String useYn;

    @Comment("등록일자")
    @CreatedDate
    private LocalDateTime createDate;

    @Comment("수정일자")
    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Builder
    public ProductBlackFriday(ProductBlackFridayDto dto) {
        if(dto.getIdx() > 0) {
            this.idx = dto.getIdx();
        }
        product = new Product();
        product.addPNum(dto.getPNum());
        this.sale = dto.getSale();
        this.useYn = dto.getUseYn();
        this.createDate = dto.getCreateDate();
        this.modifyDate = dto.getModifyDate();

    }

}
