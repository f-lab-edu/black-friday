package com.flab.blackfriday.product.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductBlackFriday is a Querydsl query type for ProductBlackFriday
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductBlackFriday extends EntityPathBase<ProductBlackFriday> {

    private static final long serialVersionUID = -1149852016L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductBlackFriday productBlackFriday = new QProductBlackFriday("productBlackFriday");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final QProduct product;

    public final NumberPath<Integer> sale = createNumber("sale", Integer.class);

    public final StringPath useYn = createString("useYn");

    public QProductBlackFriday(String variable) {
        this(ProductBlackFriday.class, forVariable(variable), INITS);
    }

    public QProductBlackFriday(Path<? extends ProductBlackFriday> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductBlackFriday(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductBlackFriday(PathMetadata metadata, PathInits inits) {
        this(ProductBlackFriday.class, metadata, inits);
    }

    public QProductBlackFriday(Class<? extends ProductBlackFriday> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

