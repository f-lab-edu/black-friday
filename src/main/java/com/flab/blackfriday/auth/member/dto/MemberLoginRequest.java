package com.flab.blackfriday.auth.member.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.auth.member.dto
 * fileName       : MemberLoginRequest
 * author         : rhkdg
 * date           : 2024-05-25
 * description    : 로그인
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-25        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class MemberLoginRequest {

    /**아이디*/
    @NotNull(message = "아이디는 필수값입니다.")
    private String id = "";

    /**비밀번호*/
    @NotNull(message = "비밀번호는 필수값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password = "";

}
