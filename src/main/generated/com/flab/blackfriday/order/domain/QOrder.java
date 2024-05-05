package com.flab.blackfriday.order.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = 225428334L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final com.flab.blackfriday.auth.member.domain.QMember member;

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final EnumPath<com.flab.blackfriday.order.dto.OrderStatusType> orderStatusType = createEnum("orderStatusType", com.flab.blackfriday.order.dto.OrderStatusType.class);

    public final EnumPath<com.flab.blackfriday.order.dto.PayStatusType> payStatusType = createEnum("payStatusType", com.flab.blackfriday.order.dto.PayStatusType.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final com.flab.blackfriday.product.domain.QProduct product;

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.flab.blackfriday.auth.member.domain.QMember(forProperty("member")) : null;
        this.product = inits.isInitialized("product") ? new com.flab.blackfriday.product.domain.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

