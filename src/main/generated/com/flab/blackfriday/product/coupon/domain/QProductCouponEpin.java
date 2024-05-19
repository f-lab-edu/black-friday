package com.flab.blackfriday.product.coupon.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductCouponEpin is a Querydsl query type for ProductCouponEpin
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductCouponEpin extends EntityPathBase<ProductCouponEpin> {

    private static final long serialVersionUID = 1407767194L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductCouponEpin productCouponEpin = new QProductCouponEpin("productCouponEpin");

    public final StringPath couponNum = createString("couponNum");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final com.flab.blackfriday.auth.member.domain.QMember member;

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final QProductCoupon productCoupon;

    public final EnumPath<com.flab.blackfriday.product.coupon.dto.CouponUseStatus> useStatus = createEnum("useStatus", com.flab.blackfriday.product.coupon.dto.CouponUseStatus.class);

    public QProductCouponEpin(String variable) {
        this(ProductCouponEpin.class, forVariable(variable), INITS);
    }

    public QProductCouponEpin(Path<? extends ProductCouponEpin> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductCouponEpin(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductCouponEpin(PathMetadata metadata, PathInits inits) {
        this(ProductCouponEpin.class, metadata, inits);
    }

    public QProductCouponEpin(Class<? extends ProductCouponEpin> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.flab.blackfriday.auth.member.domain.QMember(forProperty("member")) : null;
        this.productCoupon = inits.isInitialized("productCoupon") ? new QProductCoupon(forProperty("productCoupon")) : null;
    }

}

