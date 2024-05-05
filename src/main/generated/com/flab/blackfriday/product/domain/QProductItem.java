package com.flab.blackfriday.product.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductItem is a Querydsl query type for ProductItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductItem extends EntityPathBase<ProductItem> {

    private static final long serialVersionUID = -704711935L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductItem productItem = new QProductItem("productItem");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> pItmCnt = createNumber("pItmCnt", Integer.class);

    public final StringPath pItmName = createString("pItmName");

    public final NumberPath<Integer> pItmPrice = createNumber("pItmPrice", Integer.class);

    public final StringPath pItmRemark = createString("pItmRemark");

    public final QProduct product;

    public QProductItem(String variable) {
        this(ProductItem.class, forVariable(variable), INITS);
    }

    public QProductItem(Path<? extends ProductItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductItem(PathMetadata metadata, PathInits inits) {
        this(ProductItem.class, metadata, inits);
    }

    public QProductItem(Class<? extends ProductItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

