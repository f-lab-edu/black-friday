package com.flab.blackfriday.order.repository;

import com.flab.blackfriday.order.domain.Order;
import com.flab.blackfriday.order.domain.OrderItem;
import com.flab.blackfriday.order.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * packageName    : com.flab.blackfriday.order.repository
 * fileName       : OrderCustomRepository
 * author         : GAMJA
 * date           : 2024/05/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/10        GAMJA       최초 생성
 */
public interface OrderCustomRepository {

    /**
     * 주문 목록 조회( 페이징 o)
     * @param searchDto
     * @return
     * @throws Exception
     */
    Page<OrderSummaryResponse> selectOrderPageList(OrderDefaultDto searchDto) throws Exception;

    /**
     * 주문 목록 (페이징 x)
     * @param searchDto
     * @return
     * @throws Exception
     */
    List<OrderSummaryResponse> selectOrderList(OrderDefaultDto searchDto) throws Exception;

    /**
     * 주문 상품 목록 조회
     * @param dto
     * @return
     * @throws Exception
     */
    List<OrderItemResponse> selectOrderItemList(OrderDto dto) throws Exception;

    /**
     * 주문
     * @param dto
     * @return
     * @throws Exception
     */
    Object insertOrder(OrderDto dto) throws Exception;

    /**
     * 주문 옵션 등록
     * @param itemDto
     * @return
     * @throws Exception
     */
    boolean insertOrderItem(OrderItemDto itemDto) throws Exception;

    /**
     * 주문 상태 업데이트
     * @param dto
     * @return
     * @throws Exception
     */
    boolean updateOrderStatus(OrderDto dto) throws Exception;

    /**
     * 결제 상태 업데이트
     * @param dto
     * @return
     * @throws Exception
     */
    boolean updatePayStatus(OrderDto dto) throws Exception;

}
