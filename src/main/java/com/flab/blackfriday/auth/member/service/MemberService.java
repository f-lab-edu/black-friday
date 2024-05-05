package com.flab.blackfriday.auth.member.service;

import com.flab.blackfriday.auth.member.domain.Member;
import com.flab.blackfriday.auth.member.dto.MemberCreateRequest;
import com.flab.blackfriday.auth.member.dto.MemberDto;
import com.flab.blackfriday.auth.member.dto.MemberSummaryResponse;
import com.flab.blackfriday.auth.member.repository.MemberRepository;
import com.flab.blackfriday.common.typehandler.PasswordEncoderTypeHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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
    public void saveMember(MemberCreateRequest memberCreateRequest) throws Exception {
        Member member = memberRepository.findById(memberCreateRequest.getId()).orElse(null);
        if(member != null ){
            member.addNickname(memberCreateRequest.getNickname());
            memberRepository.save(member);
        }else {
            memberRepository.save(memberCreateRequest.toCreateEntity());
        }
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

    @Cacheable(value = "member_session", key = "#loginId")
    public MemberSummaryResponse selectMemberSession(String loginId) {
        Member member = memberRepository.findById(loginId).orElse(null);
        return member == null ? null : MemberSummaryResponse.summaryViewOf(MemberDto.of(member));
    }

}
