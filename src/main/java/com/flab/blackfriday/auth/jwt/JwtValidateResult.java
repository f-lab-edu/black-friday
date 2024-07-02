package com.flab.blackfriday.auth.jwt;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.auth.jwt
 * fileName       : JwtValidateResult
 * author         : rhkdg
 * date           : 2024-05-01
 * description    : jwt token 유효성 결과
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-01        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtValidateResult<T>{

    private JwtValidateType jwtValidateType;

    private String message;

    private T element;

    public JwtValidateResult(JwtValidateType jwtValidateType){
        this.jwtValidateType = jwtValidateType;
        this.message = jwtValidateType.getDisplay();
    }

    public JwtValidateResult(JwtValidateType jwtValidateType, T element) {
        this.jwtValidateType = jwtValidateType;
        this.message = jwtValidateType.getDisplay();
        this.element = element;
    }

}
