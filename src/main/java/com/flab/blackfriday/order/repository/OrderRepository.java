package com.flab.blackfriday.order.repository;

import com.flab.blackfriday.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.flab.blackfriday.order.repository
 * fileName       : OrderRepository
 * author         : GAMJA
 * date           : 2024/05/10
 * description    : 주문 jpa repository
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/10        GAMJA       최초 생성
 */
public interface OrderRepository extends JpaRepository<Order, Long> ,OrderCustomRepository{
}
