package com.flab.blackfriday.auth.jwt.service;

import com.flab.blackfriday.auth.admin.domain.Admin;
import com.flab.blackfriday.auth.admin.repository.AdminRepository;
import com.flab.blackfriday.auth.jwt.dto.AdminDetails;
import com.flab.blackfriday.auth.jwt.dto.MemberDetails;
import com.flab.blackfriday.auth.member.domain.Member;
import com.flab.blackfriday.auth.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.flab.blackfriday.auth.jwt.service
 * fileName       : JwtService
 * author         : rhkdg
 * date           : 2024-05-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-01        rhkdg       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtService {

    private final MemberRepository memberRepository;

    private final AdminRepository adminRepository;

    /**
     * jwt 관리자 정보
     * @param loginId
     * @return
     * @throws Exception
     */
    @Cacheable(value = "admin_jwt_login",key="#loginId")
    public AdminDetails loadAdminbyUsername(String loginId) throws Exception {
        Admin admin = adminRepository.findById(loginId).orElseThrow(() -> new UsernameNotFoundException(loginId+"-> 존재하지 않는 아이디입니다."));
        return new AdminDetails(admin);
    }

    /**
     * jwt 회원 정보
     * @param loginId
     * @return
     * @throws Exception
     */
    @Cacheable(value= "member_jwt_login",key="#loginId")
    public MemberDetails loadUserByUsername(String loginId) throws Exception {
        Member member = memberRepository.findById(loginId).orElseThrow(() -> new UsernameNotFoundException(loginId+"-> 존재하지 않는 아이디입니다."));
        return new MemberDetails(member);
    }

}
