package com.flab.blackfriday.modules.product.coupon.repository.impl;

import com.flab.blackfriday.common.BaseAbstractRepositoryImpl;
import com.flab.blackfriday.modules.product.coupon.domain.QProductCouponConfig;
import com.flab.blackfriday.modules.product.coupon.domain.QProductCouponEpin;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponDefaultDto;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponEpinWithInfoResponse;
import com.flab.blackfriday.modules.product.coupon.repository.ProductCouponEpinCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName    : com.flab.blackfriday.product.coupon.repository.impl
 * fileName       : ProductCouponEpinCustomRepositoryImpl
 * author         : GAMJA
 * date           : 2024/05/16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/16        GAMJA       최초 생성
 */
@Repository
public class ProductCouponEpinCustomRepositoryImpl extends BaseAbstractRepositoryImpl implements ProductCouponEpinCustomRepository {
    protected ProductCouponEpinCustomRepositoryImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(entityManager, jpaQueryFactory);
    }

    private BooleanBuilder commonQuery(ProductCouponDefaultDto searchDto) throws Exception {
        BooleanBuilder sql = new BooleanBuilder();
        QProductCouponEpin qProductCouponEpin = QProductCouponEpin.productCouponEpin;
        if(!StringUtils.isBlank(searchDto.getMemberId())){
            sql.and(qProductCouponEpin.member.id.eq(searchDto.getMemberId()));
        }
        if(searchDto.getProductCouponIdx() > 0) {
            sql.and(qProductCouponEpin.productCouponConfig.idx.eq(searchDto.getProductCouponIdx()));
        }
        return sql;
    }

    @Override
    public Page<ProductCouponEpinWithInfoResponse> selectProductCouponEpinPageList(ProductCouponDefaultDto searchDto) throws Exception {
        QProductCouponEpin qProductCouponEpin = QProductCouponEpin.productCouponEpin;
        QProductCouponConfig qProductCoupon = QProductCouponConfig.productCouponConfig;

        long totCnt = jpaQueryFactory.select(qProductCouponEpin.count())
                .from(qProductCouponEpin)
                .innerJoin(qProductCoupon).on(qProductCouponEpin.productCouponConfig.idx.eq(qProductCoupon.idx))
                .fetchJoin()
                .where(commonQuery(searchDto))
                .fetchFirst();

        List<ProductCouponEpinWithInfoResponse> list = jpaQueryFactory.select(
                                        Projections.constructor(
                                                ProductCouponEpinWithInfoResponse.class,
                                                qProductCouponEpin.couponNum,
                                                qProductCouponEpin.member.id,
                                                qProductCoupon.idx,
                                                qProductCoupon.title,
                                                qProductCoupon.startDate,
                                                qProductCoupon.endDate
                                        )
                                ).from(qProductCouponEpin)
                                .innerJoin(qProductCoupon).on(qProductCouponEpin.productCouponConfig.idx.eq(qProductCoupon.idx))
                                .fetchJoin()
                .where(commonQuery(searchDto))
                .offset(searchDto.getPageable().getOffset())
                .limit(searchDto.getPageable().getPageSize())
                .fetch();

        return new PageImpl<>(list,searchDto.getPageable(),totCnt);
    }

    @Override
    public List<ProductCouponEpinWithInfoResponse> selectProductCouponEpinList(ProductCouponDefaultDto searchDto) throws Exception {
        QProductCouponEpin qProductCouponEpin = QProductCouponEpin.productCouponEpin;
        QProductCouponConfig qProductCoupon = QProductCouponConfig.productCouponConfig;
        return jpaQueryFactory.select(
                        Projections.constructor(
                                ProductCouponEpinWithInfoResponse.class,
                                qProductCouponEpin.couponNum,
                                qProductCouponEpin.member.id,
                                qProductCoupon.idx,
                                qProductCoupon.title,
                                qProductCoupon.startDate,
                                qProductCoupon.endDate
                        )
                ).from(qProductCouponEpin)
                .innerJoin(qProductCoupon).on(qProductCouponEpin.productCouponConfig.idx.eq(qProductCoupon.idx))
                .fetchJoin()
                .where(commonQuery(searchDto))
                .offset(searchDto.getPageable().getOffset())
                .limit(searchDto.getPageable().getPageSize())
                .fetch();
    }

    @Override
    public long selectProductCouponEpinExistCnt(String couponNum) throws Exception {
        QProductCouponEpin qProductCouponEpin = QProductCouponEpin.productCouponEpin;
        return jpaQueryFactory.select(qProductCouponEpin.count())
                .from(qProductCouponEpin)
                .where(new BooleanBuilder().and(qProductCouponEpin.couponNum.eq(couponNum)))
                .fetchFirst();
    }
}
