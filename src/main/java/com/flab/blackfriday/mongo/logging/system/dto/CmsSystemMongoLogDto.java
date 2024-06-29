package com.flab.blackfriday.mongo.logging.system.dto;

import com.flab.blackfriday.mongo.logging.system.document.CmsSystemMongoLog;
import lombok.Getter;
import lombok.Setter;

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
public class CmsSystemMongoLogDto {

    /**일련번호*/
    private String id;

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
    public CmsSystemMongoLog toEntity() {
        return CmsSystemMongoLog.builder().dto(this).build();
    }


}
