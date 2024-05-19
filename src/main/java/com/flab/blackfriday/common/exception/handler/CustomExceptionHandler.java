package com.flab.blackfriday.common.exception.handler;

import com.flab.blackfriday.order.exception.OrderValidatorException;
import com.flab.blackfriday.order.payment.exception.PaymentFailException;
import com.flab.blackfriday.common.exception.NoExistAuthException;
import com.flab.blackfriday.common.exception.dto.ExceptionResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

/*
 * date           : 2024/05/03
 * description    : 컨트롤러 예외처리에 대한 응답처리 핸들러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/03        GAMJA       최초 생성
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 예외처리 커스텀 적용
     * @param ex
     * @return
     */
    @ExceptionHandler(NoExistAuthException.class)
    public final ResponseEntity<Object> handleNoExisException(NoExistAuthException ex) {
        ExceptionResponse exceptionResponse= new ExceptionResponse(ex.getMessage(),ex.getErrorCode());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 결제 안됨 현상 예외처리
     * @param e
     * @return
     */
    @ExceptionHandler(PaymentFailException.class)
    public ResponseEntity<?> handlerPaymentFailException(PaymentFailException e) {
        ExceptionResponse exceptionResponse= new ExceptionResponse(e.getMessage(),null);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 주문 처리시 validator 이슈
     * @param e
     * @return
     */
    @ExceptionHandler(OrderValidatorException.class)
    public ResponseEntity<?> handlerOrderValidaotrException(OrderValidatorException e) {
        ExceptionResponse exceptionResponse= new ExceptionResponse(e.getMessage(),null);
        return new ResponseEntity<>(exceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
