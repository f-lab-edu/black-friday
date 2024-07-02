package com.flab.blackfriday.auth.jwt;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.auth.jwt
 * fileName       : JwtValidateType
 * author         : rhkdg
 * date           : 2024-05-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-01        rhkdg       최초 생성
 */
@Getter
public enum JwtValidateType {

    TOKEN_OK("정상"),
    TOKEN_INVALID("잘못된 JWT 서명입니다."),
    TOKEN_EXPIRED("만료된 JWT 토큰입니다."),
    TOKEN_NOTSUPPORT("지원되지 않는 JWT 토큰입니다."),
    TOKEN_WRONG("JWT 토큰이 잘못 되었습니다");

    private String display = "";

    JwtValidateType(String display) {
        this.display = display;
    }
}
