package com.flab.blackfriday.category.repository.impl;

import com.flab.blackfriday.category.domain.Category;
import com.flab.blackfriday.category.domain.QCategory;
import com.flab.blackfriday.category.dto.CategoryDefaultDto;
import com.flab.blackfriday.category.dto.CategoryDto;
import com.flab.blackfriday.category.repository.CategoryCustomRepository;
import com.flab.blackfriday.common.BaseAbstractRepositoryImpl;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName    : com.flab.blackfriday.category.repository.impl
 * fileName       : CategoryCustomRepositoryImpl
 * author         : GAMJA
 * date           : 2024/04/19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/19        GAMJA       최초 생성
 */
@Repository
public class CategoryCustomRepositoryImpl extends BaseAbstractRepositoryImpl implements CategoryCustomRepository {
    protected CategoryCustomRepositoryImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(entityManager, jpaQueryFactory);
    }

    //공통 쿼리 처리 메소드
    public BooleanBuilder commonQuery(CategoryDefaultDto searchDto) {
        BooleanBuilder sql = new BooleanBuilder();
        QCategory qCategory = QCategory.category;

        //카테고리 코드
        if(searchDto.getCategCd() != null && !searchDto.getCategCd().isEmpty()){
            sql.and(qCategory.categCd.eq(searchDto.getCategCd()));
        }
        //부모 카테고리 코드
        if(searchDto.getParentCd() != null && !searchDto.getParentCd().isEmpty()){
            sql.and(qCategory.parentCd.eq(searchDto.getParentCd()));
        }
        //사용여부
        if(searchDto.getUseYn() != null && !searchDto.getUseYn().isEmpty()){
            sql.and(qCategory.useYn.eq(searchDto.getUseYn()));
        }

        return sql;
    }

    @Override
    public List<CategoryDto> selectCategoryList(CategoryDefaultDto searchDto) {
        QCategory qCategory = QCategory.category;
        List<Category> list = jpaQueryFactory.selectFrom(qCategory)
                .where(commonQuery(searchDto)).fetch();
        return list.stream().map(CategoryDto::new).toList();
    }

    @Override
    public long selectCategoryTotalCount(CategoryDefaultDto searchDto) {
        QCategory qCategory = QCategory.category;
        return jpaQueryFactory.select(qCategory.count())
                .from(qCategory).where(commonQuery(searchDto)).fetchFirst();
    }

}
