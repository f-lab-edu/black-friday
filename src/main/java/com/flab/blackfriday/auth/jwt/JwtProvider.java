package com.flab.blackfriday.auth.jwt;

import com.flab.blackfriday.auth.jwt.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * packageName    : com.flab.blackfriday.auth.jwt
 * fileName       : JwtProvider
 * author         : rhkdg
 * date           : 2024-05-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-01        rhkdg       최초 생성
 */
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;

    private final JwtService jwtService;

    private long tokenValidityInMilliseconds;

    private Key key;

    @PostConstruct
    protected void init() {

        //키 유효시간 30분으로 설정
        this.tokenValidityInMilliseconds = Duration.ofMinutes(30).toMillis();
        //시크릿 키값 decode 진행
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);

    }

    /**
     * token 생성
     * @param authentication
     * @param isUser
     * @return
     */
    public String createToken(Authentication authentication,boolean isUser) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        long time = now.getTime();
        Date validity = new Date(time + this.tokenValidityInMilliseconds);

        String auth = "";
        if(isUser){
            auth = "user";
        }else{
            auth = "admin";
        }

        return Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setIssuer(auth)
                .setSubject(authentication.getName())
                .claim(auth,authorities)
                .signWith(key,SignatureAlgorithm.HS256)
                .setIssuedAt(now)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(Claims claims) throws Exception {
        UserDetails principal = null;
        if(claims.getIssuer().equals("admin")) {
            principal = jwtService.loadAdminbyUsername(claims.getSubject());
        }else {
            principal = jwtService.loadUserByUsername(claims.getSubject());
        }
        return new UsernamePasswordAuthenticationToken(principal,null, principal.getAuthorities());
    }

    /**
     * HttpHeader에 담긴 Authorization 값을 가져와서 token값 분리
     * @param request
     * @return
     */
    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(bearerToken != null && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 토큰 유효성 체크
     * @param token
     * @return
     */
    public JwtValidateResult<Claims> getValidateToken(String token) {
        Claims claims = null;
        try{
            claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        }catch (SecurityException | MalformedJwtException e){
            return new JwtValidateResult<>(JwtValidateType.TOKEN_INVALID);
        }catch (ExpiredJwtException e) {
            return new JwtValidateResult<>(JwtValidateType.TOKEN_EXPIRED);
        }catch (UnsupportedJwtException e) {
            return new JwtValidateResult<>(JwtValidateType.TOKEN_NOTSUPPORT);
        }catch (IllegalArgumentException | NullPointerException e){
            return new JwtValidateResult<>(JwtValidateType.TOKEN_WRONG);
        }

        return new JwtValidateResult<>(JwtValidateType.TOKEN_OK,claims);
    }


}
