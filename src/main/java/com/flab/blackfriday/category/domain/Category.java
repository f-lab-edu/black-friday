package com.flab.blackfriday.category.domain;

import com.flab.blackfriday.category.dto.CategoryDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.category.domain
 * fileName       : Category
 * author         : rhkdg
 * date           : 2024-04-17
 * description    : 카테고리 entity
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-17        rhkdg       최초 생성
 */
@Entity
@Table(name="cms_category")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Category {

    @Id
    @Column(length = 50)
    @Comment("카테고리 코드")
    private String categCd; //카테고리코드

    @Comment("카테고리 명칭")
    @Column(length = 100)
    private String categNm; //카테고리명칭

    @Comment("정렬 순서")
    private int ord = 0; //정렬 순서

    @Comment("등록일자")
    @CreatedDate
    private LocalDateTime createDate; //등록일자

    @Comment("수정일자")
    @LastModifiedDate
    private LocalDateTime modifyDate; //수정일자

    @Builder
    public Category(CategoryDto dto) {
        this.categCd = dto.getCategCd();
        this.categNm = dto.getCategNm();
        this.ord = dto.getOrd();
        this.createDate = dto.getCreateDate();
        this.modifyDate = dto.getModifyDate();
    }

    public void addCategCd(String categCd){
        this.categCd = categCd;
    }

}
