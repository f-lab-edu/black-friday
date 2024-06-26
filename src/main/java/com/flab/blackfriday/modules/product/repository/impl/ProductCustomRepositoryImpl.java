package com.flab.blackfriday.modules.product.repository.impl;

import com.flab.blackfriday.category.domain.QCategory;
import com.flab.blackfriday.common.BaseAbstractRepositoryImpl;
import com.flab.blackfriday.modules.product.domain.ProductItem;
import com.flab.blackfriday.modules.product.domain.QProduct;
import com.flab.blackfriday.modules.product.domain.QProductBlackFriday;
import com.flab.blackfriday.modules.product.domain.QProductItem;
import com.flab.blackfriday.modules.product.dto.*;
import com.flab.blackfriday.modules.product.repository.ProductCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

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

    public ProductCustomRepositoryImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(entityManager, jpaQueryFactory);
    }

    public BooleanBuilder commonQuery(ProductDefaultDto searchDto) throws Exception {
        BooleanBuilder sql = new BooleanBuilder();
        QProduct qProduct = QProduct.product;
        QProductBlackFriday qProductBlackFriday  = QProductBlackFriday.productBlackFriday;

        if(!StringUtils.isBlank(searchDto.getSstring())){
            if(searchDto.getStype().equals("title")){
                sql.and(qProduct.pTitle.like("%"+searchDto.getSstring()+"%"));
            }
        }
        if(!StringUtils.isBlank(searchDto.getPNum())){
            sql.and(qProduct.pNum.eq(searchDto.getPNum()));
        }
        if(!StringUtils.isBlank(searchDto.getCategCd())){
            sql.and(qProduct.category.categCd.eq(searchDto.getCategCd()));
        }
        //블랙 프라이데이 사용유무
        if(!StringUtils.isBlank(searchDto.getBlackFridayUseYn())){
            sql.and(qProductBlackFriday.useYn.eq(searchDto.getBlackFridayUseYn()));
        }
        //상품 사용 여부
        if(!StringUtils.isBlank(searchDto.getUseYn())){
            sql.and(qProduct.useYn.eq(searchDto.getUseYn()));
        }
        if(!StringUtils.isBlank(searchDto.getPopulYn())){
            sql.and(qProduct.populYn.eq(searchDto.getPopulYn()));
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
    public List<ProductSummaryResponse> selectProductListWithBLackFriday(ProductDefaultDto searchDto) throws Exception {
        QProduct qProduct = QProduct.product;
        QProductBlackFriday qProductBlackFriday = QProductBlackFriday.productBlackFriday;
        QCategory qCategory = QCategory.category;
        return jpaQueryFactory.select(
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
                .leftJoin(qProductBlackFriday).on(qProduct.pNum.eq(qProductBlackFriday.product.pNum))
                .fetchJoin()
                .where(commonQuery(searchDto))
                .offset(searchDto.getPageable().getOffset())
                .limit(searchDto.getPageable().getPageSize())
                .fetch();
    }

    @Override
    public List<ProductSummaryResponse> selectProductListWithMostBlackFriday(ProductDefaultDto searchDto) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT p_num as pNum, p_title as pTitle, p.categ_cd as categCd, cc.categ_nm as categNm  FROM product p ");
        sql.append("JOIN ( SELECT categ_cd , categ_nm FROM cms_category ) cc on p.categ_cd = cc.categ_cd ");
        sql.append("JOIN product_black_friday fri on p.p_num = fri.p_num ");
        sql.append("WHERE p.use_yn = 'Y' and fri.use_yn = 'Y'");
        sql.append("Order by fri.sale DESC ");
        sql.append("limit ").append(searchDto.getPageable().getOffset()).append(",").append(searchDto.getPageable().getPageSize());

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        return jpaResultMapper.list(entityManager.createNativeQuery(sql.toString()),ProductSummaryResponse.class);
    }

    @Override
    public List<ProductTempResponse> selectProductPageWithItemTempList(ProductDefaultDto searchDto) throws Exception {
        StringBuffer sql = new StringBuffer();

        sql.append("SELECT  " +
                " p_num as pNum," +
                " idx as pitmIdx," +
                " p_itm_cnt as pCnt," +
                " p_itm_price as price" +
                " FROM (");
        sql.append(" SELECT p.*, itm.idx, itm.p_itm_cnt, itm.p_itm_price FROM product p ");
        sql.append(" JOIN ( SELECT p_num,idx,p_itm_cnt,p_itm_price FROM product_item ) itm on p.p_num = itm.p_num ");
        sql.append(" JOIN product_black_friday fri on p.p_num = fri.p_num ");
        sql.append(" ) T ").append(" LIMIT ").append(searchDto.getPageable().getOffset()).append(",").append(searchDto.getPageable().getPageSize());

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        return jpaResultMapper.list(entityManager.createNativeQuery(sql.toString()),ProductTempResponse.class);
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

        BooleanBuilder sql = new BooleanBuilder();
        if(!StringUtils.isBlank(searchDto.getPNum())){
            sql.and(qProductItem.product.pNum.eq(searchDto.getPNum()));
        }
        if(searchDto.getItemIdx() > 0) {
            sql.and(qProductItem.idx.eq(searchDto.getItemIdx()));
        }
        if(searchDto.getCnt() > 0) {
            sql.and(qProductItem.pItmCnt.goe(searchDto.getCnt()));
        }

        List<ProductItem> list = jpaQueryFactory.selectFrom(qProductItem)
                .where(sql)
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
