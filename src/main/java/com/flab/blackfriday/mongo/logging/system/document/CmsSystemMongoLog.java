package com.flab.blackfriday.mongo.logging.system.document;

import com.flab.blackfriday.mongo.logging.system.dto.CmsSystemMongoLogDto;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.logging.system.domain
 * fileName       : CmsSystemMongoLog
 * author         : rhkdg
 * date           : 2024-06-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-22        rhkdg       최초 생성
 */
@NoArgsConstructor
@Getter
@Document(collection = "cms_system_log")
public class CmsSystemMongoLog implements Persistable<String> {

    @Id
    private String id;

    @Field(name="detail")
    private String detail ;

    @Field(name = "errorDetail")
    private String errorDetail;

    @Field(name = "className")
    private String className;

    @Field(name = "methodName")
    private String methodName;

    @Field(name = "processCode")
    private String processCode;

    @Field(name = "processTime")
    private long processTime;

    @Field(name = "ip")
    private String ip;

    @CreatedDate
    private LocalDateTime createDate;

    @Builder
    public CmsSystemMongoLog(CmsSystemMongoLogDto dto) {
        this.detail = dto.getDetail();
        this.errorDetail = dto.getErrorDetail();
        this.className = dto.getClassName();
        this.methodName = dto.getMethodName();
        this.processCode = dto.getProcessCode();
        this.processTime = dto.getProcessTime();
        this.ip = dto.getIp();
    }

    @Override
    public boolean isNew() {
        return this.createDate == null;
    }
}
