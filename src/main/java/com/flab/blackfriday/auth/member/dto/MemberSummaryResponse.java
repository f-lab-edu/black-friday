package com.flab.blackfriday.auth.member.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.auth.member.dto
 * fileName       : MemberSummaryResponse
 * author         : rhkdg
 * date           : 2024-04-30
 * description    : 회원 필요 정보만 가져오기 response
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-30        rhkdg       최초 생성
 */
@Getter
@Setter
public class MemberSummaryResponse {

    /**아이디*/
    private String id = "";
    
    /**닉네임*/
    private String nickname = "";
    
    /**등록일자*/
    private LocalDateTime createDate;
    
    
    public static MemberSummaryResponse summaryViewOf(MemberDto memberDto){
        MemberSummaryResponse memberSummaryResponse = new MemberSummaryResponse();
        memberSummaryResponse.setId(memberDto.getId());
        memberSummaryResponse.setNickname(memberDto.getNickname());
        memberSummaryResponse.setCreateDate(memberDto.getCreateDate());
        return memberSummaryResponse;
    }
    
}
