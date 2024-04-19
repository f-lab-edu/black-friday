package com.flab.blackfriday.product.repository.impl;

import com.flab.blackfriday.category.domain.QCategory;
import com.flab.blackfriday.common.BaseAbstractRepositoryImpl;
import com.flab.blackfriday.product.domain.QProduct;
import com.flab.blackfriday.product.dto.ProductDefaultDto;
import com.flab.blackfriday.product.dto.ProductDto;
import com.flab.blackfriday.product.dto.ProductItemDto;
import com.flab.blackfriday.product.repository.ProductCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
        return sql;
    }

    /**
     * 상품 목록 조회 (페이징)
     * @param searchDto
     * @return
     * @throws Exception
     */
    @Override
    public Page<ProductDto> selectProductPageList(ProductDefaultDto searchDto) throws Exception {
        QProduct qProduct = QProduct.product;
        QCategory qCategory = QCategory.category;
        long totCnt = jpaQueryFactory.select(qProduct.count()).from(qProduct)
                .where(commonQuery(searchDto)).fetchFirst();

        List<Tuple> list = jpaQueryFactory.select(
                    qProduct.pNum,
                qProduct.pTitle,
                qProduct.category.categCd,
                qProduct.pContent,
                qCategory.categNm
                )
                .from(qProduct)
                .leftJoin(qCategory).on(qCategory.categCd.eq(qProduct.category.categCd))
                .where(commonQuery(searchDto))
                .offset(searchDto.getPageable().getOffset())
                .limit(searchDto.getPageable().getPageSize())
                .fetch();

        List<ProductDto> resultList = new ArrayList<>();
        for(Tuple tp : list) {
            ProductDto productDto = new ProductDto();
            productDto.setPNum(tp.get(qProduct.pNum));
            productDto.setPTitle(tp.get(qProduct.pTitle));
            productDto.setPContent(tp.get(qProduct.pContent));
            productDto.setCategCd(tp.get(qProduct.category.categCd));
            productDto.setCategNm(tp.get(qCategory.categNm));
            resultList.add(productDto);
        }

        return new PageImpl<>(resultList,searchDto.getPageable(),totCnt);
    }

    @Override
    public List<ProductDto> selectProductList(ProductDefaultDto searchDto) throws Exception {
        QProduct qProduct = QProduct.product;
        QCategory qCategory = QCategory.category;

        List<Tuple> list = jpaQueryFactory.select(
                        qProduct.pNum,
                        qProduct.pTitle,
                        qProduct.category.categCd,
                        qProduct.pContent,
                        qCategory.categNm
                )
                .from(qProduct)
                .leftJoin(qCategory).on(qCategory.categCd.eq(qProduct.category.categCd))
                .where(commonQuery(searchDto))
                .fetch();

        List<ProductDto> resultList = new ArrayList<>();
        for(Tuple tp : list) {
            ProductDto productDto = new ProductDto();
            productDto.setPNum(tp.get(qProduct.pNum));
            productDto.setPTitle(tp.get(qProduct.pTitle));
            productDto.setPContent(tp.get(qProduct.pContent));
            productDto.setCategCd(tp.get(qProduct.category.categCd));
            productDto.setCategNm(tp.get(qCategory.categNm));
            resultList.add(productDto);
        }

        return resultList;
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

        Tuple tp = (Tuple) jpaQueryFactory.select(
                        qProduct.pNum,
                        qProduct.pTitle,
                        qProduct.category.categCd,
                        qProduct.pContent,
                        qCategory.categNm,
                    qProduct.createDate,
                    qProduct.modifyDate
                )
                .from(qProduct)
                .leftJoin(qCategory).on(qCategory.categCd.eq(qProduct.category.categCd))
                .where(new BooleanBuilder().and(qProduct.pNum.eq(dto.getPNum())))
                .fetch();

        if(tp != null){
            ProductDto productDto = new ProductDto();
            productDto.setPNum(tp.get(qProduct.pNum));
            productDto.setPTitle(tp.get(qProduct.pTitle));
            productDto.setPContent(tp.get(qProduct.pContent));
            productDto.setCategNm(tp.get(qCategory.categNm));
            productDto.setCategCd(tp.get(qCategory.categCd));
            productDto.setCreateDate(tp.get(qProduct.createDate));
            productDto.setModifyDate(tp.get(qProduct.modifyDate));
            return productDto;
        }

        return null;
    }

    @Override
    public List<ProductItemDto> selectProductItemList(ProductDefaultDto searchDto) throws Exception {
        return null;
    }

    @Override
    public ProductItemDto selectProductItem(ProductItemDto dto) throws Exception {
        return null;
    }
}
