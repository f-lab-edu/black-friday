package com.flab.blackfriday.product.coupon.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductCoupon is a Querydsl query type for ProductCoupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductCoupon extends EntityPathBase<ProductCoupon> {

    private static final long serialVersionUID = -399112086L;

    public static final QProductCoupon productCoupon = new QProductCoupon("productCoupon");

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

    public QProductCoupon(String variable) {
        super(ProductCoupon.class, forVariable(variable));
    }

    public QProductCoupon(Path<? extends ProductCoupon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductCoupon(PathMetadata metadata) {
        super(ProductCoupon.class, metadata);
    }

}

