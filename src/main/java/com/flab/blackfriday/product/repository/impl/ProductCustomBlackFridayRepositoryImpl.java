package com.flab.blackfriday.product.repository.impl;

import com.flab.blackfriday.common.BaseAbstractRepositoryImpl;
import com.flab.blackfriday.product.domain.ProductBlackFriday;
import com.flab.blackfriday.product.domain.QProduct;
import com.flab.blackfriday.product.domain.QProductBlackFriday;
import com.flab.blackfriday.product.dto.ProductBlackFridayDefaultDto;
import com.flab.blackfriday.product.dto.ProductBlackFridayDto;
import com.flab.blackfriday.product.repository.ProductCustomBlackFridayRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName    : com.flab.blackfriday.product.repository.impl
 * fileName       : ProductCustomBlackFridayRepositoryImpl
 * author         : GAMJA
 * date           : 2024/04/21
 * description    : 블랙 프라이데이 repository custom
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/21        GAMJA       최초 생성
 */
@Repository
public class ProductCustomBlackFridayRepositoryImpl extends BaseAbstractRepositoryImpl implements ProductCustomBlackFridayRepository {

    protected ProductCustomBlackFridayRepositoryImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(entityManager, jpaQueryFactory);
    }

    private BooleanBuilder commonQuery(ProductBlackFridayDefaultDto searchDto) {
        BooleanBuilder sql = new BooleanBuilder();
        QProductBlackFriday qProductBlackFriday = QProductBlackFriday.productBlackFriday;

        if(searchDto.getSstring() != null && !searchDto.getSstring().isEmpty()){
            // stype 을 통해 검색 구분을 할 수 있습니다.
        }
        if(searchDto.getPNum() != null && !searchDto.getPNum().isEmpty()){
            sql.and(qProductBlackFriday.product.pNum.eq(searchDto.getPNum()));
        }
        if(searchDto.getUseYn() != null && !searchDto.getUseYn().isEmpty()) {
            sql.and(qProductBlackFriday.useYn.eq(searchDto.getUseYn()));
        }

        return sql;
    }

    @Override
    public Page<ProductBlackFridayDto> selectProductBlackFridayPageList(ProductBlackFridayDefaultDto searchDto) throws Exception {
        QProductBlackFriday qProductBlackFriday = QProductBlackFriday.productBlackFriday;
        QProduct qProduct  = QProduct.product;

        long totCnt = jpaQueryFactory.select(qProductBlackFriday.count())
                .from(qProductBlackFriday)
                .leftJoin(qProduct).on(qProductBlackFriday.product.pNum.eq(qProduct.pNum))
                .fetchJoin()
                .where(commonQuery(searchDto))
                .fetchFirst();

        List<ProductBlackFridayDto> list = jpaQueryFactory.select(
                        Projections.constructor(
                                ProductBlackFridayDto.class,
                                qProductBlackFriday.idx,
                                qProductBlackFriday.product.pNum,
                                qProductBlackFriday.sale,
                                qProductBlackFriday.useYn,
                                qProductBlackFriday.createDate,
                                qProductBlackFriday.modifyDate
                        )
                )
                .from(qProductBlackFriday)
                .leftJoin(qProduct).on(qProductBlackFriday.product.pNum.eq(qProduct.pNum))
                .fetchJoin()
                .where(commonQuery(searchDto))
                .offset(searchDto.getPageable().getOffset())
                .limit(searchDto.getPageable().getPageSize())
                .fetch();

        return new PageImpl<>(list,searchDto.getPageable(),totCnt);
    }

    @Override
    public ProductBlackFridayDto selectProductBlackFriday(ProductBlackFridayDto dto) throws Exception {
        QProductBlackFriday qProductBlackFriday = QProductBlackFriday.productBlackFriday;
        QProduct qProduct  = QProduct.product;

        ProductBlackFriday productBlackFriday = jpaQueryFactory.selectFrom(qProductBlackFriday)
                .leftJoin(qProduct).on(qProductBlackFriday.product.pNum.eq(qProduct.pNum))
                .fetchJoin()
                .where(new BooleanBuilder().and(qProductBlackFriday.idx.eq(dto.getIdx())))
                .fetchFirst();

        return productBlackFriday == null ? null : new ProductBlackFridayDto(productBlackFriday);
    }
}
