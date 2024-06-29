package com.flab.blackfriday.modules.product.coupon.repository.impl;

import com.flab.blackfriday.common.BaseAbstractRepositoryImpl;
import com.flab.blackfriday.modules.product.coupon.domain.ProductCouponConfig;
import com.flab.blackfriday.modules.product.coupon.domain.QProductCouponConfig;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponDefaultDto;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponDto;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponSummaryResponse;
import com.flab.blackfriday.modules.product.coupon.repository.ProductCouponCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName    : com.flab.blackfriday.product.coupon.repository.impl
 * fileName       : ProductCouponCustomRepositoryImpl
 * author         : GAMJA
 * date           : 2024/05/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/15        GAMJA       최초 생성
 */
@Repository
public class ProductCouponCustomRepositoryImpl extends BaseAbstractRepositoryImpl implements ProductCouponCustomRepository {


    public ProductCouponCustomRepositoryImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(entityManager, jpaQueryFactory);
    }

    private BooleanBuilder commonQuery(ProductCouponDefaultDto searchDto) throws Exception {
        QProductCouponConfig qProductCoupon = QProductCouponConfig.productCouponConfig;
        BooleanBuilder sql = new BooleanBuilder();
        if(searchDto.getSstring() != null && !searchDto.getSstring().isEmpty()){

        }
        return sql;
    }

    @Override
    public Page<ProductCouponSummaryResponse> selectProductCouponPageList(ProductCouponDefaultDto searchDto) throws Exception {
        QProductCouponConfig qProductCoupon = QProductCouponConfig.productCouponConfig;

        long totCnt = jpaQueryFactory.select(qProductCoupon.count())
                .from(qProductCoupon)
                .where(commonQuery(searchDto))
                .fetchFirst();

        List<ProductCouponSummaryResponse> list = jpaQueryFactory.select(
                        Projections.constructor(
                                ProductCouponSummaryResponse.class,
                                qProductCoupon.idx,
                                qProductCoupon.title,
                                qProductCoupon.startDate,
                                qProductCoupon.endDate,
                                qProductCoupon.couponCnt,
                                qProductCoupon.sale
                        )
                ).from(qProductCoupon).where(commonQuery(searchDto))
                .offset(searchDto.getPageable().getOffset())
                .limit(searchDto.getPageable().getPageSize())
                .fetch();

        return new PageImpl<>(list,searchDto.getPageable(),totCnt);
    }

    @Override
    public List<ProductCouponSummaryResponse> selectProductCouponList(ProductCouponDefaultDto searchDto) throws Exception {
        QProductCouponConfig qProductCoupon = QProductCouponConfig.productCouponConfig;

        return jpaQueryFactory.select(
                        Projections.constructor(
                                ProductCouponSummaryResponse.class,
                                qProductCoupon.idx,
                                qProductCoupon.title,
                                qProductCoupon.startDate,
                                qProductCoupon.endDate,
                                qProductCoupon.couponCnt,
                                qProductCoupon.sale
                        )
                ).from(qProductCoupon).where(commonQuery(searchDto))
                .offset(searchDto.getPageable().getOffset())
                .limit(searchDto.getPageable().getPageSize())
                .fetch();
    }

    @Override
    public ProductCouponDto selectProductCoupon(ProductCouponDto dto) throws Exception {
        QProductCouponConfig qProductCoupon = QProductCouponConfig.productCouponConfig;

        ProductCouponConfig productCoupon = jpaQueryFactory.selectFrom(qProductCoupon)
                .where(new BooleanBuilder().and(qProductCoupon.idx.eq(dto.getIdx())))
                .fetchOne();
        return productCoupon == null ? null : new ProductCouponDto(productCoupon);
    }
}
