package com.flab.blackfriday.product.coupon.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.coupon.domain
 * fileName       : Coupon
 * author         : GAMJA
 * date           : 2024/05/15
 * description    : 할인 쿠폰 entity
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/15        GAMJA       최초 생성
 */
@Entity
@Table(name = "product_coupon")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class ProductCoupon {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment("제목")
    private String title;

    @Comment("비고")
    private String remark;

    @Comment("시작일자")
    @Column(length = 20)
    private String startDate;

    @Comment("종료일")
    @Column(length = 20)
    private String endDate;

    @Comment("적용 카테고리 그룹")
    private String categCdGroup;

    @Comment("적용 상품 그룹")
    private String productGroup;

    @Comment("쿠폰 개수")
    private int couponCnt;

    @Comment("쿠폰 할인율")
    private int sale;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;

}
