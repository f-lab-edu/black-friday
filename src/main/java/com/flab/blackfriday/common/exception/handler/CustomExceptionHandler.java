package com.flab.blackfriday.common.exception.handler;

import com.flab.blackfriday.common.exception.NoExistAuthException;
import com.flab.blackfriday.common.exception.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * packageName    : com.flab.blackfriday.common.exception.handler
 * fileName       : CustomExceptionHandler
 * author         : GAMJA
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

}
