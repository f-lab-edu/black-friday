package com.flab.blackfriday.auth.member.dto;

import com.flab.blackfriday.auth.member.domain.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.auth.member.dto
 * fileName       : MemberDto
 * author         : rhkdg
 * date           : 2024-04-17
 * description    : 회원 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-17        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class MemberDto {
    
    /**아이디*/
    private String id = "";
    
    /**비밀번호*/
    private String password = "";
    
    /**닉네임*/
    private String nickname = "";
    
    /**등록일자*/
    private LocalDateTime createDate;
    
    /**수정일자*/
    private LocalDateTime modifyDate;

    /**dto -> entity */
    public Member toEntity(){
        return Member.builder().dto(this).build();
    }

    public static MemberDto of(Member entity){
        MemberDto dto = new MemberDto();
        dto.setId(entity.getId());
        dto.setPassword(dto.getPassword());
        dto.setNickname(dto.getNickname());
        dto.setCreateDate(dto.getCreateDate());
        dto.setModifyDate(dto.getModifyDate());
        return dto;
    }

    public MemberDto (MemberRequest req){
        MemberDto dto = new MemberDto();
        dto.setId(req.getId());
        dto.setPassword(req.getPassword());
        dto.setNickname(req.getNickname());
    }

}
