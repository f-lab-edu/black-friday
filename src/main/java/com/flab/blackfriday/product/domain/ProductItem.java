package com.flab.blackfriday.product.domain;

import com.flab.blackfriday.product.dto.ProductItemDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.product.domain
 * fileName       : ProductItem
 * author         : rhkdg
 * date           : 2024-04-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-18        rhkdg       최초 생성
 */
@Entity
@Table(name="product_item")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name="p_num",nullable = false)
    private Product product;

    @Comment("상품 옵션 제목")
    private String pItmName;

    @Comment("상품 옵션 금액")
    private int pItmPrice;

    @Comment("상품 옵션 개수")
    private int pItmCnt;

    @Comment("상품 옵션 비고")
    @Lob
    private String pItmRemark;

    @Comment("등록일자")
    @CreatedDate
    private LocalDateTime createDate;

    @Comment("수정일자")
    @LastModifiedDate
    private LocalDateTime modifyDate;


    @Builder
    public ProductItem(ProductItemDto dto) {
        if(dto.getIdx() > 0) {
            this.idx = dto.getIdx();
        }
        this.product = new Product();
        product.addPnum(dto.getPNum());
        this.pItmName = dto.getPItmName();
        this.pItmPrice = dto.getPItmPrice();
        this.pItmCnt = dto.getPItmCnt();
        this.pItmRemark = dto.getPItmRemark();
        this.createDate = dto.getCreateDate();
        this.modifyDate = dto.getModifyDate();
    }

    public void addIdx(Long idx) {
        this.idx = idx;
    }

    /**
     * 개수 +
     * @param cnt
     */
    public void plusItemCnt(int cnt) {
        this.pItmCnt += cnt;
    }

    /**
     * 개수 - 처리
     * @param cnt
     */
    public void minusItemCnt(int cnt) {
        this.pItmCnt -= cnt;
    }

}
