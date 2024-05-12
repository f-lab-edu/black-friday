package com.flab.blackfriday.product.repository.impl;

import com.flab.blackfriday.category.domain.QCategory;
import com.flab.blackfriday.common.BaseAbstractRepositoryImpl;
import com.flab.blackfriday.product.domain.ProductItem;
import com.flab.blackfriday.product.domain.QProduct;
import com.flab.blackfriday.product.domain.QProductBlackFriday;
import com.flab.blackfriday.product.domain.QProductItem;
import com.flab.blackfriday.product.dto.ProductDefaultDto;
import com.flab.blackfriday.product.dto.ProductDto;
import com.flab.blackfriday.product.dto.ProductItemDto;
import com.flab.blackfriday.product.dto.ProductSummaryResponse;
import com.flab.blackfriday.product.repository.ProductCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : com.flab.blackfriday.product.repository.impl
 * fileName       : ProductCustomRepositoryImpl
 * author         : rhkdg
 * date           : 2024-04-19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-19        rhkdg       최초 생성
 */
@Repository
public class ProductCustomRepositoryImpl extends BaseAbstractRepositoryImpl implements ProductCustomRepository {
    protected ProductCustomRepositoryImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(entityManager, jpaQueryFactory);
    }


    public BooleanBuilder commonQuery(ProductDefaultDto searchDto) throws Exception {
        BooleanBuilder sql = new BooleanBuilder();
        QProduct qProduct = QProduct.product;
        QProductBlackFriday qProductBlackFriday  = QProductBlackFriday.productBlackFriday;
        if(searchDto.getSstring() != null && !searchDto.getSstring().isEmpty()){
            if(searchDto.getStype().equals("title")){
                sql.and(qProduct.pTitle.like("%"+searchDto.getSstring()+"%"));
            }
        }
        if(searchDto.getPNum() != null && !searchDto.getPNum().isEmpty()){
            sql.and(qProduct.pNum.eq(searchDto.getPNum()));
        }
        if(searchDto.getCategCd() != null && !searchDto.getCategCd().isEmpty()){
            sql.and(qProduct.category.categCd.eq(searchDto.getCategCd()));
        }
        //블랙 프라이데이 사용유무
        if(searchDto.getBlackFridayUseYn() != null && !searchDto.getBlackFridayUseYn().isEmpty()){
            sql.and(qProductBlackFriday.useYn.eq(searchDto.getBlackFridayUseYn()));
        }
        return sql;
    }

    /**
     * 상품 목록 조회 (페이징)
     * @param searchDto
     * @return
     * @throws Exception
     */
    @Override
    public Page<ProductSummaryResponse> selectProductPageList(ProductDefaultDto searchDto) throws Exception {
        QProduct qProduct = QProduct.product;
        QCategory qCategory = QCategory.category;
        long totCnt = jpaQueryFactory.select(qProduct.count()).from(qProduct)
                .where(commonQuery(searchDto)).fetchFirst();

        List<ProductSummaryResponse> list = jpaQueryFactory.select(
                    Projections.constructor(ProductSummaryResponse.class,
                            qProduct.pNum,
                            qProduct.pTitle,
                            qProduct.category.categCd,
                            qCategory.categNm
                    )
                )
                .from(qProduct)
                .leftJoin(qCategory).on(qCategory.categCd.eq(qProduct.category.categCd))
                .fetchJoin()
                .where(commonQuery(searchDto))
                .offset(searchDto.getPageable().getOffset())
                .limit(searchDto.getPageable().getPageSize())
                .fetch();

        return new PageImpl<>(list,searchDto.getPageable(),totCnt);
    }

    @Override
    public Page<ProductSummaryResponse> selectProductPageListWithBlackFriday(ProductDefaultDto searchDto) throws Exception {
        QProduct qProduct = QProduct.product;
        QProductBlackFriday qProductBlackFriday = QProductBlackFriday.productBlackFriday;
        QCategory qCategory = QCategory.category;

        long totCnt = jpaQueryFactory.select(qProduct.count())
                .from(qProduct)
                .join(qCategory).on(qProduct.category.categCd.eq(qCategory.categCd))
                .fetchJoin()
                .join(qProductBlackFriday).on(qProduct.pNum.eq(qProductBlackFriday.product.pNum))
                .fetchJoin()
                .where(commonQuery(searchDto)).fetchFirst();

        List<ProductSummaryResponse> list = jpaQueryFactory.select(
                        Projections.fields(ProductSummaryResponse.class,
                                qProduct.pNum,
                                qProduct.pTitle,
                                qProduct.category.categCd,
                                qCategory.categNm,
                                qProductBlackFriday.sale
                        )
                )
                .from(qProduct)
                .join(qCategory).on(qProduct.category.categCd.eq(qCategory.categCd))
                .fetchJoin()
                .join(qProductBlackFriday).on(qProduct.pNum.eq(qProductBlackFriday.product.pNum))
                .fetchJoin()
                .where(commonQuery(searchDto))
                .offset(searchDto.getPageable().getOffset())
                .limit(searchDto.getPageable().getPageSize())
                .fetch();

        return new PageImpl<>(list,searchDto.getPageable(),totCnt);
    }

    @Override
    public List<ProductSummaryResponse> selectProductList(ProductDefaultDto searchDto) throws Exception {
        QProduct qProduct = QProduct.product;
        QCategory qCategory = QCategory.category;

        List<ProductSummaryResponse> list = jpaQueryFactory.select(
                        Projections.constructor(ProductSummaryResponse.class,
                                qProduct.pNum,
                                qProduct.pTitle,
                                qProduct.category.categCd,
                                qCategory.categNm
                        )
                )
                .from(qProduct)
                .leftJoin(qCategory).on(qCategory.categCd.eq(qProduct.category.categCd))
                .fetchJoin()
                .where(commonQuery(searchDto))
                .fetch();

        return list;
    }

    /**
     * 상세 조회
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    public ProductDto selectProduct(ProductDto dto) throws Exception {
        QProduct qProduct = QProduct.product;
        QCategory qCategory = QCategory.category;

        return jpaQueryFactory.select(
                    Projections.constructor(
                            ProductDto.class,
                            qProduct.pNum,
                            qProduct.category.categCd,
                            qCategory.categNm,
                            qProduct.pTitle,
                            qProduct.pContent,
                            qProduct.ord,
                            qProduct.useYn,
                            qProduct.createDate,
                            qProduct.modifyDate
                    )
                )
                .from(qProduct)
                .leftJoin(qCategory).on(qCategory.categCd.eq(qProduct.category.categCd)).fetchJoin()
                .where(new BooleanBuilder().and(qProduct.pNum.eq(dto.getPNum())))
                .fetchOne();
    }

    @Override
    public List<ProductItemDto> selectProductItemList(ProductDefaultDto searchDto) throws Exception {
        QProductItem qProductItem = QProductItem.productItem;
        List<ProductItem> list = jpaQueryFactory.selectFrom(qProductItem)
                .where(new BooleanBuilder().and(qProductItem.product.pNum.eq(searchDto.getPNum())))
                .fetch();
        return list.stream().map(ProductItemDto::new).toList();
    }

    @Override
    public ProductItemDto selectProductItem(ProductItemDto dto) throws Exception {
        QProductItem qProductItem = QProductItem.productItem;
        ProductItem item = jpaQueryFactory.selectFrom(qProductItem)
                .where(new BooleanBuilder().and(qProductItem.idx.eq(dto.getIdx())))
                .fetchFirst();
        return item == null ? null : new ProductItemDto(item);
    }

    @Override
    public boolean updateProductItemPcnt(ProductItemDto itemDto) throws Exception {
        QProductItem qProductItem = QProductItem.productItem;
        return jpaQueryFactory.update(qProductItem)
                .set(qProductItem.pItmCnt,itemDto.getPItmCnt())
                .where(new BooleanBuilder().and(qProductItem.idx.eq(itemDto.getIdx())))
                .execute() > 0;
    }
}
