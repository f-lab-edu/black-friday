package com.flab.blackfriday.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * packageName    : com.flab.blackfriday.config
 * fileName       : JPAConfig
 * author         : rhkdg
 * date           : 2024-06-24
 * description    : JPA 엔티티 및 트랜잭션 커스텀 설정
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-24        rhkdg       최초 생성 , 멀티 데이터베이스를 사용하기 떄문에 JPA 트랜잭션 커스텀 추가
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "com.flab.blackfriday.**.*.repository",
        entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef = "mysqlTx"
)
@EnableTransactionManagement
public class JPAConfig {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl_auto;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String url;

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory() {
            LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
            em.setDataSource(mysqlDatasource());
            em.setPackagesToScan("com.flab.blackfriday.**.*.domain");
//            em.setPersistenceUnitName("mysqlEntityManager");

            HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
            vendorAdapter.setGenerateDdl(true);
            em.setJpaVendorAdapter(vendorAdapter); // JPA 구현체인 hibernate를 사용하기 위해 등록해준다.

            HashMap<String, Object> properties = new HashMap<>();
            properties.put("hibernate.hbm2ddl.auto", ddl_auto);
            properties.put("default_batch_fetch_size", 500);
            //엔티티에 선언된 변수들 네이밍을 자동 변환하여 디비에 연동하는 처리 설정
            properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
            // 해당 아래 정보는 Deprecated 된 패키지 입니다. (Spring boot 3 에서는 사용안됨 CamelCaseToUnderscoresNamingStrategy 위의 정보를 사용) 
            // properties.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
            properties.put("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
//            properties.put("hibernate.show_sql","true");
//            properties.put("hibernate.format_sql","true");
            em.setJpaPropertyMap(properties);
            return em;
    }

    @Primary
    @Bean  // (2)
    public DataSource mysqlDatasource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Primary
    @Bean(name = "mysqlTx")  // (3)
    public JpaTransactionManager mysqlTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(mysqlEntityManagerFactory().getObject());
        return transactionManager;
    }

}
