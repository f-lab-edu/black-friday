package com.flab.blackfriday.mongo.logging.system.service;

import com.flab.blackfriday.logging.system.DetailLoggerSetter;
import com.flab.blackfriday.mongo.logging.system.dto.CmsSystemMongoLogDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Order(2)
@Component
@RequiredArgsConstructor
public class CmsSystemMongoLogAspect {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CmsSystemMongoLogService cmsSystemLogService;
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
    @Around("execution(public * com.flab.blackfriday.mongo.modules..*service.*Service.insert*(..))")
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
    @Around("execution(public * com.flab.blackfriday.mongo.modules..*service.*Service.update*(..))")
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
    @Around("execution(public * com.flab.blackfriday.mongo.modules..*service.*Service.delete*(..))")
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
            System.out.println("---------insert log start mongo db -------------");
            CmsSystemMongoLogDto cmsSystemLogDto = new CmsSystemMongoLogDto();
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
