package com.flab.blackfriday.common;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * packageName    : com.flab.blackfriday.common
 * fileName       : BaseAbstractRepositoryImpl
 * author         : rhkdg
 * date           : 2024-04-19
 * description    : 공통 처리 repository impl
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-19        rhkdg       최초 생성
 */
public abstract class BaseAbstractRepositoryImpl {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final EntityManager entityManager;

    protected final JPAQueryFactory jpaQueryFactory;

    protected BaseAbstractRepositoryImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        this.entityManager = entityManager;
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
