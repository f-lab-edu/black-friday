package com.flab.blackfriday.modules.product.coupon.dto;

import com.flab.blackfriday.logging.system.DetailLoggerSetter;
import com.flab.blackfriday.modules.product.coupon.domain.ProductCouponConfig;
import com.flab.blackfriday.modules.product.coupon.dto.action.ProductCouponConfigDeleteRequest;
import com.flab.blackfriday.modules.product.coupon.dto.action.ProductCouponConfigRequest;
import com.flab.blackfriday.modules.product.coupon.dto.action.ProductCouponConfigUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.product.coupon.dto
 * fileName       : ProductCouponDto
 * author         : GAMJA
 * date           : 2024/05/15
 * description    : 쿠폰 세팅 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/15        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCouponDto implements DetailLoggerSetter {

    private long idx = 0L;

    private String title = "";

    private String remark = "";

    private String startDate = "";

    private String endDate = "";

    private String categCdGroup = "";

    private String productGroup = "";

    private int couponCnt = 0;

    private int sale = 0;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    public ProductCouponConfig toCreateEntity() {
        return ProductCouponConfig.createOf(this);
    }

    public ProductCouponDto(ProductCouponConfig entity) {
        this.idx = entity.getIdx();
        this.title = entity.getTitle();
        this.remark = entity.getRemark();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.categCdGroup = entity.getCategCdGroup();
        this.productGroup = entity.getProductGroup();
        this.couponCnt = entity.getCouponCnt();
        this.sale = entity.getSale();
    }

    /**
     * 등록 메소드
     * @param create
     * @return
     */
    public static ProductCouponDto createOf(ProductCouponConfigRequest create) {
        ProductCouponDto request = new ProductCouponDto();
        request.setTitle(create.getTitle());
        request.setRemark(create.getRemark());
        request.setStartDate(create.getStartDate());
        request.setEndDate(create.getEndDate());
        request.setCategCdGroup(create.getCategCdGroup());
        request.setProductGroup(create.getProductGroup());
        request.setCouponCnt(create.getCouponCnt());
        request.setProductGroup(create.getProductGroup());
        request.setCouponCnt(create.getCouponCnt());
        request.setSale(create.getSale());
        return request;
    }

    /**
     * 수정 메소드
     * @param update
     * @return
     */
    public static ProductCouponDto updateOf(ProductCouponConfigUpdateRequest update) {
        ProductCouponDto request = new ProductCouponDto();
        request.setIdx(update.getIdx());
        request.setTitle(update.getTitle());
        request.setRemark(update.getRemark());
        request.setStartDate(update.getStartDate());
        request.setEndDate(update.getEndDate());
        request.setCategCdGroup(update.getCategCdGroup());
        request.setProductGroup(update.getProductGroup());
        request.setCouponCnt(update.getCouponCnt());
        request.setProductGroup(update.getProductGroup());
        request.setCouponCnt(update.getCouponCnt());
        request.setSale(update.getSale());
        return request;
    }

    /**
     * 삭제 메소드
     * @param delete
     * @return
     */
    public static ProductCouponDto deleteOf(ProductCouponConfigDeleteRequest delete) {
        ProductCouponDto request = new ProductCouponDto();
        request.setIdx(delete.getIdx());
        return request;
    }

    @Override
    public String printSystemString() {
        return "ProductCouponDto{" +
                "idx=" + idx +
                ", title='" + title + '\'' +
                ", remark='" + remark + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", categCdGroup='" + categCdGroup + '\'' +
                ", productGroup='" + productGroup + '\'' +
                ", couponCnt=" + couponCnt +
                ", sale=" + sale +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                '}';
    }
}
