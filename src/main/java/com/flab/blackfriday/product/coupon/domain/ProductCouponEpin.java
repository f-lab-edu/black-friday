package com.flab.blackfriday.product.coupon.domain;

import com.flab.blackfriday.auth.member.domain.Member;
import com.flab.blackfriday.product.coupon.dto.CouponUseStatus;
import com.flab.blackfriday.product.coupon.dto.CouponUseType;
import com.flab.blackfriday.product.coupon.dto.ProductCouponEpinDto;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private ProductCouponConfig productCouponConfig;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Comment("쿠폰형태")
    @Enumerated(EnumType.STRING)
    private CouponUseType useType;

    @Comment("사용상태")
    @Enumerated(EnumType.STRING)
    private CouponUseStatus useStatus;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Builder
    public ProductCouponEpin(ProductCouponEpinDto epinDto) {
        this.couponNum = epinDto.getCouponNum();
        this.productCouponConfig = new ProductCouponConfig();
        productCouponConfig.addIdx(epinDto.getIdx());
        if(!StringUtils.isBlank(epinDto.getId())){
            this.member = new Member();
            member.addId(epinDto.getId());
        }
        this.useType = CouponUseType.valueOf(epinDto.getUseType());
        this.useStatus = CouponUseStatus.valueOf(epinDto.getUseStatus());
    }

    public void addUpdateEpin(ProductCouponEpinDto epinDto) {
        this.member.addId(epinDto.getId());
        this.useStatus = CouponUseStatus.valueOf(epinDto.getUseStatus());
    }

    @Override
    public String getId() {
        return this.couponNum;
    }

    @Override
    public boolean isNew() {
        return createDate == null;
    }
}
