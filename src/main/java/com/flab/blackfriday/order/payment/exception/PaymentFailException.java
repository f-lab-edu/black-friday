package com.flab.blackfriday.order.payment.exception;

/**
 * packageName    : com.flab.blackfriday.order.payment.exception
 * fileName       : PaymentFailException
 * author         : GAMJA
 * date           : 2024/05/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/10        GAMJA       최초 생성
 */
public class PaymentFailException extends RuntimeException{
    public PaymentFailException(String message) {
        super(message);
    }
}
