package com.flab.blackfriday.auth.member.dto;

import com.flab.blackfriday.auth.member.domain.Member;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.auth.member.dto
 * fileName       : MemberRequest
 * author         : rhkdg
 * date           : 2024-04-19
 * description    : 특정 validation이 적용되어야 하는 경우 사용
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-19        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class MemberRequest {

    /**아이디*/
    @NotNull(message = "아이디는 필수값입니다.")
    private String id = "";

    /**비밀번호*/
    @NotNull(message = "비밀번호는 필수값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password = "";

    /**닉네임*/
    @NotNull(message = "닉네임은 필수값입니다.")
    private String nickname = "";

}
