package com.flab.blackfriday.modules.order.exception;

import lombok.Getter;

/**
 * packageName    : com.flab.blackfriday.order.exception
 * fileName       : OrderValidatorException
 * author         : rhkdg
 * date           : 2024-05-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-11        rhkdg       최초 생성
 */
public class OrderValidatorException extends RuntimeException{
    public OrderValidatorException(String message){
        super(message);
    }
}
