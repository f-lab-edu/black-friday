package com.flab.blackfriday.auth.member.service;

import com.flab.blackfriday.auth.member.domain.Member;
import com.flab.blackfriday.auth.member.dto.MemberDto;
import com.flab.blackfriday.auth.member.repository.MemberRepository;
import com.flab.blackfriday.common.typehandler.PasswordEncoderTypeHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.flab.blackfriday.auth.member.service
 * fileName       : MemberService
 * author         : rhkdg
 * date           : 2024-04-18
 * description    : 회원 service
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-18        rhkdg       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 상세 정보 조회
     * @param dto
     * @return
     * @throws Exception
     */
    public MemberDto selectMember(MemberDto dto) throws Exception {
        Member member = memberRepository.findById(dto.getId()).orElse(null);
        if (member == null) {
            return null;
        }
        return MemberDto.of(member);
    }

    /**
     * 등록, 수정
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public void saveMember(MemberDto dto) throws Exception {
        Member member = memberRepository.findById(dto.getId()).orElse(null);
        if(member == null ){
            //비밀번호 암호화 처리
            if(dto.getPassword() != null && !dto.getPassword().isEmpty()) {
                String password = dto.getPassword();
                dto.setPassword(PasswordEncoderTypeHandler.encode(password));
            }
        }
        memberRepository.save(dto.toEntity());
    }

    /**
     * 삭제
     * @param dto
     * @throws Exception
     */
    @Transactional
    public void deleteMember(MemberDto dto) throws Exception {
        memberRepository.deleteById(dto.getId());
    }

}
