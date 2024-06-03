package com.flab.blackfriday.order.service;

import com.flab.blackfriday.order.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.flab.blackfriday.order.service
 * fileName       : OrderLockService
 * author         : rhkdg
 * date           : 2024-06-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-02        rhkdg       최초 생성
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderLockService {

    private final OrderService orderService;

    /**
     * 주문 처리 낙관적 lock test
     * @param dto
     * @throws Exception
     */
    @Transactional
    public void insertOrderOptimisticLock(OrderDto dto) throws Exception {
        while(true) {
            try{
                orderService.insertOrderOptimisticLock(dto);
            }catch (ObjectOptimisticLockingFailureException e) {
                log.error("### 낙관적 락 버전 불일치 발생 ### {}",e.getMessage());
                Thread.sleep(10);
            }
        }
    }

}
