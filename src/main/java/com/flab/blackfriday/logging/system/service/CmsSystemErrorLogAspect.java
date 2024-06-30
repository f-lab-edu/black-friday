package com.flab.blackfriday.logging.system.service;

import com.flab.blackfriday.logging.system.DetailLoggerSetter;
import com.flab.blackfriday.logging.system.dto.CmsSystemLogDto;
import com.flab.blackfriday.logging.system.repository.CmsSystemLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
@Order(3)
@Component
@RequiredArgsConstructor
//@ConditionalOnExpression("${endpoint.aspect.enabled:true}")
public class CmsSystemErrorLogAspect {

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

    @AfterThrowing(value = "execution(public * com.flab.blackfriday.modules..service.*Service.*(..))",throwing = "e")
    public Object errorLog(JoinPoint joinPoint , Exception e)  {
        System.out.println("## exception : "+e.getClass().getName());
        //명확히 Exception 에러가 발생하 였을 경우 처리
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            errorlog(joinPoint, stopWatch, "E", e);
            return joinPoint;
        } catch (Throwable ex) {
            throw ex;
        }
    }


    /**
     * 로그 처리 메소드
     * @param joinPoint
     * @param stopWatch
     * @param processCode
     */
    private void errorlog(JoinPoint joinPoint, StopWatch stopWatch, String processCode, Exception ex) {

        if(stopWatch.isRunning()){
            stopWatch.stop();
        }

        long totalTime = stopWatch.getTotalTimeMillis();

        try{
            System.out.println("---------insert error log start mysql -------------");
            CmsSystemLogDto cmsSystemLogDto = new CmsSystemLogDto();
            cmsSystemLogDto.setProcessCode(processCode);
            cmsSystemLogDto.setProcessTime(totalTime);
            cmsSystemLogDto.setIp(getIp());
            cmsSystemLogDto.setClassName(joinPoint.getTarget().getClass().getName());
            cmsSystemLogDto.setMethodName(joinPoint.getSignature().getName());
            cmsSystemLogDto.setErrorDetail(ex.getMessage());
            //detail 정보 처리
            if(joinPoint.getArgs().length > 0) {
                Class<?> clazz = joinPoint.getArgs()[0].getClass();
                if(DetailLoggerSetter.class.isAssignableFrom(clazz)){
                    DetailLoggerSetter logger = (DetailLoggerSetter) joinPoint.getArgs()[0];
                    cmsSystemLogDto.setDetail(logger.printSystemString());
                }
            }
            cmsSystemLogService.createCmsSystemLog(cmsSystemLogDto);
            System.out.println("-----------------------end-----------------");
        }catch (Exception e) {
            logger.error("[System logging Aop] throw 시스템 로그 저장시 오류가 발생했습니다. 확인 바랍니다.");
        }
    }

}
