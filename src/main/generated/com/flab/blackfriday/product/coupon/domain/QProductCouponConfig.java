package com.flab.blackfriday.product.coupon.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductCouponConfig is a Querydsl query type for ProductCouponConfig
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductCouponConfig extends EntityPathBase<ProductCouponConfig> {

    private static final long serialVersionUID = -108462004L;

    public static final QProductCouponConfig productCouponConfig = new QProductCouponConfig("productCouponConfig");

    public final StringPath categCdGroup = createString("categCdGroup");

    public final NumberPath<Integer> couponCnt = createNumber("couponCnt", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath endDate = createString("endDate");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final StringPath productGroup = createString("productGroup");

    public final StringPath remark = createString("remark");

    public final NumberPath<Integer> sale = createNumber("sale", Integer.class);

    public final StringPath startDate = createString("startDate");

    public final StringPath title = createString("title");

    public QProductCouponConfig(String variable) {
        super(ProductCouponConfig.class, forVariable(variable));
    }

    public QProductCouponConfig(Path<? extends ProductCouponConfig> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductCouponConfig(PathMetadata metadata) {
        super(ProductCouponConfig.class, metadata);
    }

}

