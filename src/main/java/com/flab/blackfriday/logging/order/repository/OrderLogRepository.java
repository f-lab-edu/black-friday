package com.flab.blackfriday.logging.order.repository;

import com.flab.blackfriday.logging.order.domain.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.flab.blackfriday.logging.order.repository
 * fileName       : OrderLogRepository
 * author         : rhkdg
 * date           : 2024-06-20
 * description    : 주문 정보 로그
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-20        rhkdg       최초 생성
 */
public interface OrderLogRepository extends JpaRepository<OrderLog,Long> {
}
