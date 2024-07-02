package com.flab.blackfriday.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
public class BaseException extends RuntimeException{

    private String className = "";

    private String methodName = "";

    public BaseException() {

    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, String className, String methodName){
        super(message);
        this.className = className;
        this.methodName = className+"."+methodName;
    }



}
