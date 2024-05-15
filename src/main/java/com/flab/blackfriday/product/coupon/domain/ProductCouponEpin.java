package com.flab.blackfriday.product.coupon.domain;

import com.flab.blackfriday.auth.member.domain.Member;
import com.flab.blackfriday.product.coupon.dto.CouponUseStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.product.coupon.domain
 * fileName       : ProductCouponEpin
 * author         : GAMJA
 * date           : 2024/05/15
 * description    : 쿠폰 생성 entity
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/15        GAMJA       최초 생성
 */
@Entity
@Table(name="product_coupon_epin")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductCouponEpin implements Persistable<String> {

    @Id
    private String couponNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idx")
    private ProductCoupon productCoupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Comment("사용상태")
    @Enumerated(EnumType.STRING)
    private CouponUseStatus useStatus;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Override
    public String getId() {
        return this.couponNum;
    }

    @Override
    public boolean isNew() {
        return createDate == null;
    }
}
