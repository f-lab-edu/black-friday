package com.flab.blackfriday.product.repository.impl;

import com.flab.blackfriday.common.BaseAbstractRepositoryImpl;
import com.flab.blackfriday.product.dto.ProductDefaultDto;
import com.flab.blackfriday.product.dto.ProductDto;
import com.flab.blackfriday.product.dto.ProductItemDto;
import com.flab.blackfriday.product.repository.ProductCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
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
    protected ProductCustomRepositoryImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(entityManager, jpaQueryFactory);
    }

    @Override
    public Page<ProductDto> selectProductPageList(ProductDefaultDto searchDto) throws Exception {
        return null;
    }

    @Override
    public List<ProductDto> selectProductList(ProductDefaultDto searchDto) throws Exception {
        return null;
    }

    @Override
    public ProductDto selectProduct(ProductDto dto) throws Exception {
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
