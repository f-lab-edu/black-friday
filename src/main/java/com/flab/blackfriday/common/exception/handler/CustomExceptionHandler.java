package com.flab.blackfriday.common.exception.handler;

import com.flab.blackfriday.common.exception.BaseException;
import com.flab.blackfriday.common.exception.CommonNotUseException;
import com.flab.blackfriday.logging.system.dto.CmsSystemLogDto;
import com.flab.blackfriday.logging.system.service.CmsSystemLogService;
import com.flab.blackfriday.modules.order.exception.OrderValidatorException;
import com.flab.blackfriday.modules.order.payment.exception.PaymentFailException;
import com.flab.blackfriday.common.exception.NoExistAuthException;
import com.flab.blackfriday.common.exception.dto.ExceptionResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final CmsSystemLogService cmsSystemLogService;

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
     * 공통 에외처리
     * @param ex
     * @return
     */
    @ExceptionHandler(CommonNotUseException.class)
    public final ResponseEntity<Object> handleCommonNotUseException(CommonNotUseException ex) {
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


    /**
     * 예외처리 핸들러
     * @param e
     * @param request
     */
//    @ExceptionHandler({BaseException.class})
//    public ResponseEntity<?> handlerLoggingException(BaseException e, HttpServletRequest request) {
//        try {
//            CmsSystemLogDto cmsSystemLogDto = new CmsSystemLogDto();
//            cmsSystemLogDto.setProcessCode("E");
//            cmsSystemLogDto.setIp(request.getRemoteAddr());
//            cmsSystemLogDto.setClassName(e.getClassName());
//            cmsSystemLogDto.setMethodName(e.getMethodName());
//            cmsSystemLogDto.setErrorDetail(e.getMessage());
//            cmsSystemLogService.createCmsSystemLog(cmsSystemLogDto);
//        }catch (Exception ex){
//            logger.error("[System logging Aop] 시스템 로그 저장시 오류가 발생했습니다. 확인 바랍니다.");
//        }
//        ExceptionResponse exceptionResponse= new ExceptionResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.name());
//        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handlerLoggingException(Exception e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.name());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
