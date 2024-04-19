package com.flab.blackfriday.auth.admin.domain;

import com.flab.blackfriday.auth.admin.dto.AdminDto;
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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.auth.admin.domain
 * fileName       : Admin
 * author         : rhkdg
 * date           : 2024-04-18
 * description    : 관리자 entity
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-18        rhkdg       최초 생성
 */
@Entity
@Table(name="cms_admin")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Admin {

    @Id
    private String id;

    @Comment("비밀번호")
    private String password;

    @Comment("등록일자")
    @CreatedDate
    private LocalDateTime createDate;

    @Comment("수정일자")
    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Builder
    public Admin(AdminDto dto) {
        this.id = dto.getId();
        this.password = dto.getPassword();
        this.createDate = dto.getCreateDate();
        this.modifyDate = dto.getModifyDate();
    }
}
