package com.flab.blackfriday.common.typehandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * packageName    : com.flab.blackfriday.common.typehandler
 * fileName       : PasswordEncoderTypeHandler
 * author         : rhkdg
 * date           : 2024-04-19
 * description    : password 암호화 처리 handler
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-19        rhkdg       최초 생성
 */
public class PasswordEncoderTypeHandler{

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordEncoderTypeHandler.class);

    private static final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    /**
     * spring
     * @param s 입력 문자열
     * @return 암호화된 문자열
     */
    public static String encode(String s) {
        return passwordEncoder.encode(s);
    }

    /**
     * 인코드 패스워드 체크
     * @param password
     * @param encodedPassword
     * @return
     */
    public static boolean matches(String password, String encodedPassword) {

        boolean result = false;
        try {
            result = passwordEncoder.matches(password,encodedPassword);
        }catch (Exception e) {
            LOGGER.error("### encode check error : {}",e.getMessage());
        }

        return result;
    }
}
