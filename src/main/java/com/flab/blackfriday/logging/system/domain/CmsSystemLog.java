package com.flab.blackfriday.logging.system.domain;

import com.flab.blackfriday.logging.system.dto.CmsSystemLogDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.logging.system.domain
 * fileName       : CmsSystemLog
 * author         : rhkdg
 * date           : 2024-06-21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-21        rhkdg       최초 생성
 */
@Entity
@Table(name="cms_system_log")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CmsSystemLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment("상세 데이터 정보")
    private String detail ;
    
    @Comment("상세 에러 정보")
    private String errorDetail;

    @Comment("클래스 정보")
    private String className;

    @Comment("메소드 정보")
    private String methodName;

    @Comment("실행 결과 코드")
    @Column(length = 1)
    private String processCode;

    @Comment("실행 시간")
    private long processTime;

    @Comment("실행 ip")
    private String ip;

    @Comment("등록 일자")
    @CreatedDate
    private LocalDateTime createDate;

    @Builder
    public CmsSystemLog(CmsSystemLogDto dto) {
        this.idx = dto.getIdx();
        this.detail = dto.getDetail();
        this.errorDetail = dto.getErrorDetail();
        this.className = dto.getClassName();
        this.methodName = dto.getMethodName();
        this.processCode = dto.getProcessCode();
        this.processTime = dto.getProcessTime();
        this.ip = dto.getIp();
    }

}
