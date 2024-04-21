package com.flab.blackfriday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// security 때문에 권한으로 인해 접근안됨을 방지하기 위해 우선 security에 대해서 exclude 선언
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableJpaAuditing
public class BlackfridayApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BlackfridayApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BlackfridayApplication.class);
    }

}
