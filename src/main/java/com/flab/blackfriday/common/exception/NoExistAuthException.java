package com.flab.blackfriday.common.exception;

import lombok.Getter;

/**
 * packageName    : com.flab.blackfriday.common.exception
 * fileName       : NoExistAuthException
 * author         : GAMJA
 * date           : 2024/05/03
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/03        GAMJA       최초 생성
 */
@Getter
public class NoExistAuthException extends RuntimeException{

    /**에러코드*/
    private String errorCode = "";

    public NoExistAuthException() {

    }

    public NoExistAuthException(String message) {
        super(message);
    }

    public NoExistAuthException(String message, String errorCode){
        super(message);
        this.errorCode = errorCode;
    }



}
