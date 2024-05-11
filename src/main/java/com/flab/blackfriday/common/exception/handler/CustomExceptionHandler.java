package com.flab.blackfriday.common.exception.handler;

import com.flab.blackfriday.order.exception.OrderValidatorException;
import com.flab.blackfriday.order.payment.exception.PaymentFailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * packageName    : com.flab.blackfriday.common.exception.handler
 * fileName       : CustomExceptionHandler
 * author         : GAMJA
 * date           : 2024/05/10
 * description    : 커스텀 예외처리 response 처리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/10        GAMJA       최초 생성
 */
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * 결제 안됨 현상 예외처리
     * @param e
     * @return
     */
    @ExceptionHandler(PaymentFailException.class)
    public ResponseEntity<?> handlerPaymentFailException(PaymentFailException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 주문 처리시 validator 이슈
     * @param e
     * @return
     */
    @ExceptionHandler(OrderValidatorException.class)
    public ResponseEntity<?> handlerOrderValidaotrException(OrderValidatorException e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
