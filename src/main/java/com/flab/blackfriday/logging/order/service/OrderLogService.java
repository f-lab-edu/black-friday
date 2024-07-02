package com.flab.blackfriday.logging.order.service;

import com.flab.blackfriday.logging.order.domain.OrderLog;
import com.flab.blackfriday.logging.order.dto.OrderLogDto;
import com.flab.blackfriday.logging.order.repository.OrderLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.flab.blackfriday.logging.order.service
 * fileName       : AccessOrderLogService
 * author         : rhkdg
 * date           : 2024-06-20
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-20        rhkdg       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true,value = "mysqlTx")
public class OrderLogService {

    private final OrderLogRepository orderLogRepository;

    /**
     * 주문 로그 정보
     * @param orderLogDto
     */
    @Transactional(value = "mysqlTx")
    public void insertOrderLog(OrderLogDto orderLogDto) {
        OrderLog orderLog = new OrderLog(orderLogDto);
        orderLogRepository.save(orderLog);
    }

}
