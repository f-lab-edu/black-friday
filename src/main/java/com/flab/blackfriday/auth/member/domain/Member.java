package com.flab.blackfriday.auth.member.domain;

import com.flab.blackfriday.auth.member.dto.MemberDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.auth.member.domain
 * fileName       : Member
 * author         : GAMJA
 * date           : 2024/04/17
 * description    : 회원 entity
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/17        GAMJA       최초 생성
 */
@Entity
@Table(name="member")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member implements Persistable<String> {

    @Id
    @Comment("회원아이디")
    private String id;

    @Comment("비밀번호")
    private String password;

    @Comment("닉네임")
    private String nickname;

    @CreatedDate
    @Comment("등록일자")
    private LocalDateTime createDate;

    @LastModifiedDate
    @Comment("수정일자")
    private LocalDateTime modifyDate;

    @Builder
    public Member(MemberDto dto) {
        this.id = dto.getId();
        this.password = dto.getPassword();
        this.nickname = dto.getNickname();
        this.createDate = dto.getCreateDate();
        this.modifyDate = dto.getModifyDate();
    }

    public void addNickname(String nickname) {
        this.nickname = nickname;
    }

    public void addId(String id){
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return this.id.isBlank();
    }
}
