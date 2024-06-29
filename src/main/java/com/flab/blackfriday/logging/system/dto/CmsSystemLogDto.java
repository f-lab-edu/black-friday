package com.flab.blackfriday.logging.system.dto;

import com.flab.blackfriday.logging.system.domain.CmsSystemLog;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.flab.blackfriday.logging.system.dto
 * fileName       : CmsSystemLogDto
 * author         : rhkdg
 * date           : 2024-06-21
 * description    : 시스템 처리 결과를 관리하는 log dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-21        rhkdg       최초 생성
 */
@Getter
@Setter
public class CmsSystemLogDto {

    /**일련번호*/
    private Long idx = 0L;

    /**상세 데이터*/
    private String detail ;

    private String errorDetail;

    /**클래스 이름*/
    private String className;

    /**메소드 이름*/
    private String methodName;

    /**프로세스 코드*/
    private String processCode;

    /**프로세스 시간*/
    private long processTime;

    /**아이피 정보*/
    private String ip;

    /**
     * dto -> entity
     * @return
     */
    public CmsSystemLog toEntity() {
        return CmsSystemLog.builder().dto(this).build();
    }

    public Map<String,Object> toMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("className",this.className);
        map.put("detail",this.detail);
        map.put("errorDetail",this.errorDetail);
        map.put("methodName",this.methodName);
        map.put("processCode",this.processCode);
        map.put("processTime",this.processTime);
        map.put("ip",this.ip);
        return map;
    }

}
