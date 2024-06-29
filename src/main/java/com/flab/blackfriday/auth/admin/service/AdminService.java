package com.flab.blackfriday.auth.admin.service;

import com.flab.blackfriday.auth.admin.domain.Admin;
import com.flab.blackfriday.auth.admin.dto.AdminCreateRequest;
import com.flab.blackfriday.auth.admin.dto.AdminDto;
import com.flab.blackfriday.auth.admin.dto.AdminResponse;
import com.flab.blackfriday.auth.admin.repository.AdminRepository;
import com.flab.blackfriday.auth.member.domain.Member;
import com.flab.blackfriday.auth.member.dto.MemberDto;
import com.flab.blackfriday.auth.member.dto.MemberSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.flab.blackfriday.auth.admin.service
 * fileName       : AdminService
 * author         : GAMJA
 * date           : 2024/05/03
 * description    : 관리자 service
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/03        GAMJA       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true,value = "mysqlTx")
public class AdminService {

    private final AdminRepository adminRepository;

    /**
     * 관리자 상세 정보 조회
     * @param dto
     * @return
     * @throws Exception
     */
    public AdminDto selectAdmin(AdminDto dto) throws Exception {
        Admin admin = adminRepository.findById(dto.getId()).orElse(null);
        if(admin == null){
            return null;
        }
        return AdminDto.of(admin);
    }

    /**
     * 등록,수정
     * @throws Exception
     */
    @Transactional(value = "mysqlTx")
    public void saveAdmin(AdminCreateRequest adminCreateRequest) throws Exception {
        Admin admin = adminRepository.findById(adminCreateRequest.getId()).orElse(null);
        if(admin != null){
            admin.addName(adminCreateRequest.getName());
            admin.addPassword(adminCreateRequest.getPassword());
            adminRepository.save(admin);
        }else{
            adminRepository.save(adminCreateRequest.toCreateEntity());
        }
    }

    /**
     * 삭제
     * @param dto
     * @throws Exception
     */
    @Transactional(value = "mysqlTx")
    public void deleteMember(AdminDto dto) throws Exception {
        adminRepository.deleteById(dto.getId());
    }

    @Cacheable(value = "admin_session", key = "#loginId")
    public AdminResponse selectAdminSession(String loginId) {
        Admin admin = adminRepository.findById(loginId).orElse(null);
        return admin == null ? null : AdminResponse.of(AdminDto.of(admin));
    }
}
