package com.flab.blackfriday.logging.system.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.blackfriday.auth.member.dto.MemberSession;
import com.flab.blackfriday.common.dto.ResultVO;
import com.flab.blackfriday.common.exception.BaseException;
import com.flab.blackfriday.logging.order.dto.OrderLogDto;
import com.flab.blackfriday.logging.order.service.OrderLogService;
import com.flab.blackfriday.logging.system.DetailLoggerSetter;
import com.flab.blackfriday.logging.system.dto.CmsSystemLogDto;
import com.flab.blackfriday.logging.system.repository.CmsSystemLogRepository;
import com.flab.blackfriday.modules.order.dto.OrderStatusType;
import com.flab.blackfriday.modules.order.dto.action.OrderCreateRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

/**
 * packageName    : com.flab.blackfriday.logging.order
 * fileName       : AccessOrderAspect
 * author         : rhkdg
 * date           : 2024-06-20
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-20        rhkdg       최초 생성
 */
@Aspect
@Order(1)
@Component
@RequiredArgsConstructor
public class CmsSystemLogAspect {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CmsSystemLogService cmsSystemLogService;
    /**
     * 아이피 검출
     * @return
     */
    private String getIp() {
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            return request.getRemoteAddr();
        }catch (Exception e){
            return "NaN";
        }
    }

    /**
     * 등록 정보 처리
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(public * com.flab.blackfriday.modules..service.*Service.insert*(..))")
    public Object insertLog(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();
        try{

            stopWatch.start();

            Object objValue = joinPoint.proceed();

            log(joinPoint,stopWatch,"I");

            return objValue;
        }catch (Throwable e) {
            throw e;
        }
    }

    /**
     * 수정 정보 처리
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(public * com.flab.blackfriday.modules..service.*Service.update*(..))")
    public Object updateLog(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();
        try{

            stopWatch.start();

            Object objValue = joinPoint.proceed();

            log(joinPoint,stopWatch,"U");

            return objValue;
        }catch (Throwable e) {
            throw e;
        }
    }

    /**
     * 삭제 정보 처리
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(public * com.flab.blackfriday.modules..service.*Service.delete*(..))")
    public Object deleteLog(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();
        try{

            stopWatch.start();

            Object objValue = joinPoint.proceed();

            log(joinPoint,stopWatch,"D");
            return objValue;
        }catch (Throwable e) {
            throw e;
        }
    }


    /**
     * 로그 처리 메소드
     * @param joinPoint
     * @param stopWatch
     * @param processCode
     */
    private void log(ProceedingJoinPoint joinPoint, StopWatch stopWatch, String processCode) {

        if(stopWatch.isRunning()){
            stopWatch.stop();
        }

        long totalTime = stopWatch.getTotalTimeMillis();

        try{
            System.out.println("---------insert log start-------------");
            CmsSystemLogDto cmsSystemLogDto = new CmsSystemLogDto();
            cmsSystemLogDto.setProcessCode(processCode);
            cmsSystemLogDto.setProcessTime(totalTime);
            cmsSystemLogDto.setIp(getIp());
            cmsSystemLogDto.setClassName(joinPoint.getTarget().getClass().getName());
            cmsSystemLogDto.setMethodName(joinPoint.getSignature().getName());
            //detail 정보 처리
            if(joinPoint.getArgs().length > 0) {
                Class<?> clazz = joinPoint.getArgs()[0].getClass();
                if(DetailLoggerSetter.class.isAssignableFrom(clazz)){
                    //파라미터 값에 해당
                    DetailLoggerSetter logger = (DetailLoggerSetter) joinPoint.getArgs()[0];
                    cmsSystemLogDto.setDetail(logger.printSystemString());
                }
            }
            cmsSystemLogService.createCmsSystemLog(cmsSystemLogDto);
            System.out.println("-----------------------end-----------------");
        }catch (Exception e) {
            logger.error("[System logging Aop] 시스템 로그 저장시 오류가 발생했습니다. 확인 바랍니다.");
        }
    }



}
