package com.flab.blackfriday.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * packageName    : com.flab.blackfriday.config
 * fileName       : CacheConfig
 * author         : rhkdg
 * date           : 2024-05-01
 * description    : 캐싱 처리 bean
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-01        rhkdg       최초 생성
 */
@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        cacheManager.setAllowNullValues(false);
        cacheManager.setCacheNames(List.of(
                "admin_jwt_login",
                "member_jwt_login",
                "member_session",
                "product_popular",
                "product_most_blackfriday",
                "category_list",
                "productVersion"));
        return cacheManager;
    }
}
