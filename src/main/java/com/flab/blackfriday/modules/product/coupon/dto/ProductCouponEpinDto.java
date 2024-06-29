package com.flab.blackfriday.modules.product.coupon.dto;

import com.flab.blackfriday.logging.system.DetailLoggerSetter;
import com.flab.blackfriday.modules.product.coupon.domain.ProductCouponEpin;
import com.flab.blackfriday.modules.product.coupon.dto.action.ProductCouponEpinUpdateRequest;
import com.flab.blackfriday.modules.product.coupon.dto.action.ProductCouponEpinRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.product.coupon.dto
 * fileName       : ProductCouponEpinDto
 * author         : GAMJA
 * date           : 2024/05/15
 * description    : 쿠폰 생성 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/15        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCouponEpinDto implements DetailLoggerSetter {

    private String couponNum = "";

    private long idx = 0L;

    private String id = "";

    private String useType = "";

    private String useStatus = "";

    private ProductCouponDto productCouponDto;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    public ProductCouponEpin toEntity() {
        return ProductCouponEpin.builder().epinDto(this).build();
    }

    public ProductCouponEpinDto(ProductCouponEpin entity){
        this.couponNum = entity.getCouponNum();
        this.idx = entity.getProductCouponConfig().getIdx();
        this.id = entity.getMember().getId();
        this.useType = entity.getUseType().name();
        this.useStatus = entity.getUseStatus().name();
        this.createDate = entity.getCreateDate();
        this.modifyDate = entity.getModifyDate();
    }

    /**
     * 쿠폰 생성
     * @param create
     * @return
     */
    public static ProductCouponEpinDto createOf(ProductCouponEpinRequest create) {
        ProductCouponEpinDto dto = new ProductCouponEpinDto();
        dto.setId(create.getId());
        dto.setIdx(create.getIdx());
        dto.setUseStatus(create.getUseStatus());
        dto.setUseType(create.getUseType());
        return dto;
    }

    public static ProductCouponEpinDto updateOf(ProductCouponEpinUpdateRequest update) {
        ProductCouponEpinDto dto = new ProductCouponEpinDto();
        dto.setCouponNum(update.getCouponNum());
        dto.setId(update.getId());
        dto.setIdx(update.getIdx());
        return dto;
    }

    @Override
    public String printSystemString() {
        return "ProductCouponEpinDto{" +
                "couponNum='" + couponNum + '\'' +
                ", idx=" + idx +
                ", id='" + id + '\'' +
                ", useType='" + useType + '\'' +
                ", useStatus='" + useStatus + '\'' +
                ", productCouponDto=" + productCouponDto +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                '}';
    }
}
