package com.flab.blackfriday.product.coupon.domain;

import com.flab.blackfriday.product.coupon.dto.ProductCouponDto;
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
 * description    : 할인 쿠폰 설정 entity
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/15        GAMJA       최초 생성
 */
@Entity
@Table(name = "product_coupon_config")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class ProductCouponConfig {

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

    public void addProductUpdate(ProductCouponDto dto) {
        this.idx = dto.getIdx();
        this.title = dto.getTitle();
        this.remark = dto.getRemark();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
        this.categCdGroup = dto.getCategCdGroup();
        this.productGroup = dto.getProductGroup();
        this.couponCnt = dto.getCouponCnt();
        this.sale = dto.getSale();
    }

    public void addProductCreate(ProductCouponDto dto) {
        this.title = dto.getTitle();
        this.remark = dto.getRemark();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
        this.categCdGroup = dto.getCategCdGroup();
        this.productGroup = dto.getProductGroup();
        this.couponCnt = dto.getCouponCnt();
        this.sale = dto.getSale();
    }

    public static ProductCouponConfig createOf(ProductCouponDto dto) {
        ProductCouponConfig productCoupon = new ProductCouponConfig();
        productCoupon.addProductCreate(dto);
        return productCoupon;
    }

    public void addIdx(long idx) {
        this.idx = idx;
    }

    /**
     * 쿠폰 마이너스 처리
     */
    public void minusCnt(int cnt) {
        this.couponCnt -= cnt;
    }

}
