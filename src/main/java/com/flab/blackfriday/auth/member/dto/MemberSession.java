package com.flab.blackfriday.auth.member.dto;

import com.flab.blackfriday.auth.jwt.JwtProvider;
import com.flab.blackfriday.auth.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.flab.blackfriday.auth.member.dto
 * fileName       : MemeberSession
 * author         : GAMJA
 * date           : 2024/04/19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/19        GAMJA       최초 생성
 */
@Component
@RequiredArgsConstructor
public class MemberSession {

    private final JwtProvider jwtProvider;

    private final MemberService memberService;

    private Authentication authentication = null;

    /**
     * 회원 토큰 생성
     * @param dto
     * @return
     */
    public String createToken(MemberDto dto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getId(),dto.getPassword());
        return jwtProvider.createToken(authenticationToken,true);
    }
    
    public boolean isAuthenticated() {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null;
    }

    public MemberSummaryResponse getMemberSession(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        return memberService.selectMemberSession(authentication.getName());
    }

}
