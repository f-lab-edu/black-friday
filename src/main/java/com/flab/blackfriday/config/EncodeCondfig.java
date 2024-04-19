package com.flab.blackfriday.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * packageName    : com.flab.blackfriday.config
 * fileName       : EncodeCondifg
 * author         : rhkdg
 * date           : 2024-04-19
 * description    : 패스워드 인코드를 위한 bean 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-19        rhkdg       최초 생성
 */
@Configuration
public class EncodeCondfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
