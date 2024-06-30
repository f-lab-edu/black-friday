package com.flab.blackfriday.logging.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.blackfriday.auth.member.dto.MemberSession;
import com.flab.blackfriday.common.dto.ResultVO;
import com.flab.blackfriday.logging.order.dto.OrderLogDto;
import com.flab.blackfriday.modules.order.dto.OrderStatusType;
import com.flab.blackfriday.modules.order.dto.action.OrderCreateRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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
@Component
@RequiredArgsConstructor
public class AccessOrderAspect {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderLogService orderLogService;

    /**
     * 주문 진행 시 로그 생성
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.flab.blackfriday.modules.order.controller.OrderUserController.*(..))")
    public Object orderLogInsert(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        Object obj = joinPoint.proceed();
        Object[] parameterValues = joinPoint.getArgs();

        OrderCreateRequest orderCreateRequest = null;
        for(int i = 0; i < method.getParameters().length; i++){
            String parameterName = method.getParameters()[i].getName();
            if(parameterName.equals("orderCreateRequest")){
                orderCreateRequest = (OrderCreateRequest) parameterValues[i];
            }
        }

        try{
            ResponseEntity<?> response = (ResponseEntity<?>) obj;
            if(orderCreateRequest != null){
                ResultVO<?> resultVO = (ResultVO<?>) response.getBody();
                if(resultVO != null) {
                    OrderLogDto orderLogDto = new OrderLogDto();
                    ObjectMapper objectMapper = new ObjectMapper();
                    orderLogDto.setParentIdx((Long) resultVO.getElement());
                    orderLogDto.setOrderData(objectMapper.writeValueAsString(orderCreateRequest));
                    orderLogDto.setOrderStatus(OrderStatusType.NONE.name());
                    orderLogService.insertOrderLog(orderLogDto);
                }
            }
        }catch (Exception e) {
            logger.error("### order log insert error : {}",e.getMessage());
        }

        return obj;
    }

}
