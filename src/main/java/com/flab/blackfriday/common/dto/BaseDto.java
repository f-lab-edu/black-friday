package com.flab.blackfriday.common.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.flab.blackfriday.common.dto
 * fileName       : BaseDto
 * author         : rhkdg
 * date           : 2024-04-18
 * description    : 공통 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-18        rhkdg       최초 생성
 */
@Getter
@Setter
public class BaseDto implements Serializable {

    /**공통 입력*/
    protected String sstring = "";

    /**공통 타입*/
    protected String stype = "";

    /**페이지 번호*/
    protected int page = 1;

    /**페이지 크기*/
    protected int size = 10;

    /**정렬*/
    protected Sort sort = null;

    protected Map<String,String> params = new HashMap<>();

    public Pageable getPageable() {
        if(sort != null) {
            return PageRequest.of((this.page - 1),this.size,sort);
        }
        return PageRequest.of((this.page - 1),this.size);
    }
    
}
