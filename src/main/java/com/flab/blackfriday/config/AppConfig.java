package com.flab.blackfriday.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.flab.blackfriday.config
 * fileName       : Appconfig
 * author         : rhkdg
 * date           : 2024-04-19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-19        rhkdg       최초 생성
 */
@Configuration
public class AppConfig {

//    @PersistenceContext(unitName = "mysqlEntityManager")
    @PersistenceContext
    public EntityManager entityManager;

    /**
     *query dsl
     * @return
     */
    @Bean
    public JPAQueryFactory queryFactory() {
        return new JPAQueryFactory(entityManager);
    }

}
