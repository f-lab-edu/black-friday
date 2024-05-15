package com.flab.blackfriday.product.coupon.repository.impl;

import com.flab.blackfriday.common.BaseAbstractRepositoryImpl;
import com.flab.blackfriday.product.coupon.dto.ProductCouponDefaultDto;
import com.flab.blackfriday.product.coupon.dto.ProductCouponEpinDto;
import com.flab.blackfriday.product.coupon.repository.ProductCouponEpinCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;

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
public class ProductCouponEpinCustomRepositoryImpl extends BaseAbstractRepositoryImpl implements ProductCouponEpinCustomRepository {
    protected ProductCouponEpinCustomRepositoryImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(entityManager, jpaQueryFactory);
    }

    @Override
    public Page<ProductCouponEpinDto> selectProductCouponEpinPageList(ProductCouponDefaultDto searchDto) throws Exception {
        return null;
    }

    @Override
    public List<ProductCouponEpinDto> selectProductCouponEpinList(ProductCouponDefaultDto searchDto) throws Exception {
        return null;
    }

    @Override
    public ProductCouponEpinDto selectProductCouponEpin(ProductCouponEpinDto dto) throws Exception {
        return null;
    }
}
