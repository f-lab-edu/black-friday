package com.flab.blackfriday.auth.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.flab.blackfriday.auth.jwt
 * fileName       : JwtProperties
 * author         : rhkdg
 * date           : 2024-05-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-01        rhkdg       최초 생성
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String header;

    private String secretKey;

}
