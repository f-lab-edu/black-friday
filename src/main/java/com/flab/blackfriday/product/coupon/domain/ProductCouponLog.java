package com.flab.blackfriday.product.coupon.domain;

import com.flab.blackfriday.auth.member.domain.Member;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.product.coupon.domain
 * fileName       : ProductCouponLog
 * author         : GAMJA
 * date           : 2024/05/15
 * description    : 쿠폰 사용 log
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/15        GAMJA       최초 생성
 */
@Entity
@Table(name="product_coupon_log")
@EntityListeners(AuditingEntityListener.class)
public class ProductCouponLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="coupon_num", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ProductCouponEpin productCouponEpin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="coupon_idx", nullable = false , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ProductCouponConfig productCouponConfig;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id",  nullable = false,  foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;


}
