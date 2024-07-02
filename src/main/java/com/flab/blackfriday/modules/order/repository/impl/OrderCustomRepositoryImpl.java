package com.flab.blackfriday.modules.order.repository.impl;

import com.flab.blackfriday.auth.member.domain.QMember;
import com.flab.blackfriday.category.domain.QCategory;
import com.flab.blackfriday.common.BaseAbstractRepositoryImpl;
import com.flab.blackfriday.modules.order.domain.QOrder;
import com.flab.blackfriday.modules.order.domain.QOrderItem;
import com.flab.blackfriday.modules.order.dto.*;
import com.flab.blackfriday.modules.order.repository.OrderCustomRepository;
import com.flab.blackfriday.modules.product.domain.QProduct;
import com.flab.blackfriday.modules.product.domain.QProductItem;
import com.flab.blackfriday.modules.product.dto.ProductItemSummaryResponse;
import com.flab.blackfriday.modules.product.dto.ProductSummaryResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * packageName    : com.flab.blackfriday.order.repository.impl
 * fileName       : OrderCustomRepositoryImpl
 * author         : GAMJA
 * date           : 2024/05/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/10        GAMJA       최초 생성
 */
@Repository
public class OrderCustomRepositoryImpl extends BaseAbstractRepositoryImpl implements OrderCustomRepository {

    public OrderCustomRepositoryImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(entityManager, jpaQueryFactory);
    }

    public BooleanBuilder commonQuery(OrderDefaultDto searchDto) {
        BooleanBuilder sql = new BooleanBuilder();
        QMember qMember = QMember.member;
        QOrder qOrder = QOrder.order;
        if(searchDto.getId() != null && !searchDto.getId().isEmpty()){
            sql.and(qOrder.member.id.eq(searchDto.getId()));
        }

        return sql;
    }

    @Override
    public Page<OrderSummaryResponse> selectOrderPageList(OrderDefaultDto searchDto) throws Exception {
        QOrder qOrder = QOrder.order;
        QProduct qProduct = QProduct.product;
        QCategory qCategory = QCategory.category;

        long totCnt = jpaQueryFactory.select(qOrder.count()).from(qOrder)
                .join(qProduct).on(qProduct.pNum.eq(qOrder.product.pNum))
                .fetchJoin()
                .where(commonQuery(searchDto))
                .fetchFirst();

        List<OrderSummaryResponse> list = jpaQueryFactory.select(
                        Projections.constructor(
                                OrderSummaryResponse.class,
                                qOrder.idx,
                                Projections.constructor(
                                  ProductSummaryResponse.class,
                                        qProduct.pNum,
                                        qProduct.pTitle,
                                        qProduct.category.categCd,
                                        qProduct.category.categNm
                                ),
                                qOrder.orderStatusType.stringValue(),
                                qOrder.payStatusType.stringValue(),
                                qOrder.price
                        )
                )
                .from(qOrder)
                .join(qProduct).on(qProduct.pNum.eq(qOrder.product.pNum))
                .fetchJoin()
                .join(qCategory).on(qCategory.categCd.eq(qProduct.category.categCd))
                .fetchJoin()
                .where(commonQuery(searchDto))
                .offset(searchDto.getPageable().getOffset())
                .limit(searchDto.getPageable().getPageSize())
                .fetch();

        return new PageImpl<>(list,searchDto.getPageable(),totCnt);
    }

    @Override
    public List<OrderSummaryResponse> selectOrderList(OrderDefaultDto searchDto) throws Exception {
        QOrder qOrder = QOrder.order;
        QProduct qProduct = QProduct.product;
        QCategory qCategory = QCategory.category;

        return jpaQueryFactory.select(
                        Projections.constructor(
                                OrderSummaryResponse.class,
                                qOrder.idx,
                                Projections.constructor(
                                        ProductSummaryResponse.class,
                                        qProduct.pNum,
                                        qProduct.pTitle,
                                        qProduct.category.categCd,
                                        qProduct.category.categNm
                                ),
                                qOrder.orderStatusType.stringValue(),
                                qOrder.payStatusType.stringValue(),
                                qOrder.price
                        )
                )
                .from(qOrder)
                .join(qProduct).on(qProduct.pNum.eq(qOrder.product.pNum))
                .fetchJoin()
                .join(qCategory).on(qCategory.categCd.eq(qProduct.category.categCd))
                .fetchJoin()
                .where(commonQuery(searchDto))
                .offset(searchDto.getPageable().getOffset())
                .limit(searchDto.getPageable().getPageSize())
                .fetch();
    }

    @Override
    public List<OrderItemResponse> selectOrderItemList(OrderDto dto) throws Exception {
        QOrderItem qOrderItem = QOrderItem.orderItem;
        QProductItem qProductItem = QProductItem.productItem;
        return jpaQueryFactory.select(
                    Projections.constructor(
                            OrderItemResponse.class,
                            qOrderItem.idx,
                            qOrderItem.order.idx,
                            Projections.constructor(
                                    ProductItemSummaryResponse.class,
                                    qProductItem.idx,
                                    qProductItem.pItmName,
                                    qProductItem.pItmPrice,
                                    qProductItem.pItmCnt,
                                    qProductItem.pItmRemark
                            ),
                            qOrderItem.pCnt,
                            qOrderItem.price,
                            qOrderItem.createDate,
                            qOrderItem.modifyDate
                    )
                ).from(qOrderItem)
                .join(qProductItem).on(qProductItem.idx.eq(qOrderItem.productItem.idx))
                .where(new BooleanBuilder().and(qOrderItem.order.idx.eq(dto.getIdx())))
                .fetchJoin()
                .fetch();
    }

    @Override
    public boolean insertOrderItem(OrderItemDto itemDto) throws Exception {
        return entityManager.createNativeQuery("insert into order_item (o_idx,pitm_idx,p_cnt,price,create_date,modify_date) values (?,?,?,?,?,?)")
                .setParameter(1,itemDto.getOIdx())
                .setParameter(2,itemDto.getPitmIdx())
                .setParameter(3,itemDto.getPCnt())
                .setParameter(4,itemDto.getPrice())
                .setParameter(5,LocalDateTime.now())
                .setParameter(6,LocalDateTime.now())
                .executeUpdate() > 0;
    }

    @Override
    public boolean updateOrderStatus(OrderDto dto) throws Exception {
        QOrder qOrder = QOrder.order;

        return jpaQueryFactory.update(qOrder)
                .set(qOrder.orderStatusType, OrderStatusType.valueOf(dto.getOrderStatus()))
                .where(qOrder.idx.eq(dto.getIdx())).execute() > 0;
    }

    @Override
    public boolean updatePayStatus(OrderDto dto) throws Exception {
        QOrder qOrder = QOrder.order;

        return jpaQueryFactory.update(qOrder)
                .set(qOrder.payStatusType, PayStatusType.valueOf(dto.getPayStatus()))
                .where(qOrder.idx.eq(dto.getIdx())).execute() > 0;
    }
}
