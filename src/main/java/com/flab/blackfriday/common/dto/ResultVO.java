package com.flab.blackfriday.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.common.dto
 * fileName       : ResultVO
 * author         : rhkdg
 * date           : 2024-05-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-11        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO {

    /**상태코드*/
    private String statusCode;

    /**메세지*/
    private String message;
    
    /**object 요소*/
    private Object element;

    public ResultVO(String statusCode) {
        this.statusCode = statusCode;
    }

    public ResultVO(String statusCode , String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}
