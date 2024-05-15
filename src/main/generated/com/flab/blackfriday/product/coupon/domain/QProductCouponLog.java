package com.flab.blackfriday.product.coupon.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductCouponLog is a Querydsl query type for ProductCouponLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductCouponLog extends EntityPathBase<ProductCouponLog> {

    private static final long serialVersionUID = -1478602118L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductCouponLog productCouponLog = new QProductCouponLog("productCouponLog");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final com.flab.blackfriday.auth.member.domain.QMember member;

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final QProductCoupon productCoupon;

    public final QProductCouponEpin productCouponEpin;

    public QProductCouponLog(String variable) {
        this(ProductCouponLog.class, forVariable(variable), INITS);
    }

    public QProductCouponLog(Path<? extends ProductCouponLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductCouponLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductCouponLog(PathMetadata metadata, PathInits inits) {
        this(ProductCouponLog.class, metadata, inits);
    }

    public QProductCouponLog(Class<? extends ProductCouponLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.flab.blackfriday.auth.member.domain.QMember(forProperty("member")) : null;
        this.productCoupon = inits.isInitialized("productCoupon") ? new QProductCoupon(forProperty("productCoupon")) : null;
        this.productCouponEpin = inits.isInitialized("productCouponEpin") ? new QProductCouponEpin(forProperty("productCouponEpin"), inits.get("productCouponEpin")) : null;
    }

}

