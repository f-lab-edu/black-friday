package com.flab.blackfriday.auth.jwt.filter;

import com.flab.blackfriday.auth.jwt.JwtProvider;
import com.flab.blackfriday.auth.jwt.JwtValidateResult;
import com.flab.blackfriday.auth.jwt.JwtValidateType;
import com.flab.blackfriday.auth.jwt.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * packageName    : com.flab.blackfriday.auth.jwt.filter
 * fileName       : JwtAuthenticationFilter
 * author         : rhkdg
 * date           : 2024-05-01
 * description    : 토큰 정보 check 하는 filter
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-01        rhkdg       최초 생성
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.resolveToken(request);
        String requestURI = request.getRequestURI();
        JwtValidateResult<Claims> result = jwtProvider.getValidateToken(token);
        if(StringUtils.hasText(token) && JwtValidateType.TOKEN_OK.name().equals(result.getJwtValidateType().name())){
            try{
                Authentication authentication = jwtProvider.getAuthentication(result.getElement());
                UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
                authToken.setDetails(request);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }catch (Exception e) {
                logger.error("### 인증 정보 조회시 이슈 발생 ###");
            }
        }else {
            logger.error("### 토큰 인증 정보 실패 : "+result.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
