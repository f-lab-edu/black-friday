package com.flab.blackfriday.auth.admin.dto;

import com.flab.blackfriday.auth.admin.service.AdminService;
import com.flab.blackfriday.auth.jwt.JwtProvider;
import com.flab.blackfriday.auth.member.dto.MemberDto;
import com.flab.blackfriday.auth.member.dto.MemberSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.flab.blackfriday.auth.admin.dto
 * fileName       : AdminSession
 * author         : GAMJA
 * date           : 2024/05/03
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/03        GAMJA       최초 생성
 */
@Component
@RequiredArgsConstructor
public class AdminSession {

    private final JwtProvider jwtProvider;

    private final AdminService adminService;

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

    public AdminResponse getAdminSession(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        return adminService.selectAdminSession(authentication.getName());
    }


}
