package com.flab.blackfriday.order.service;

import com.flab.blackfriday.order.domain.Order;
import com.flab.blackfriday.order.dto.*;
import com.flab.blackfriday.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : com.flab.blackfriday.order.service
 * fileName       : OrderService
 * author         : GAMJA
 * date           : 2024/05/10
 * description    : 주문 service
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/10        GAMJA       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     *  주문 목록 조회(페이징 o)
     * @param searchDto
     * @return
     * @throws Exception
     */
    public Page<OrderSummaryResponse> selectOrderPageList(OrderDefaultDto searchDto) throws Exception {
        return orderRepository.selectOrderPageList(searchDto);
    }

    /**
     * 주문 목록 조회(페이징 x)
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<OrderSummaryResponse> selectOrderList(OrderDefaultDto searchDto) throws Exception{
        return orderRepository.selectOrderList(searchDto);
    }

    /**
     * 주문 상세 정보 조회
     * @param orderDto
     * @return
     * @throws Exception
     */
    public OrderDto selectOrder(OrderDto orderDto) throws Exception {
        Order order = orderRepository.findById(orderDto.getIdx()).orElse(null);
        if(order != null){
            orderDto = new OrderDto(order);
            List<OrderItemResponse> itemList = this.selectOrderItemList(orderDto);
            if(itemList != null && !itemList.isEmpty()) {
                orderDto.setItemList(itemList.stream().map(OrderItemDto::responseOf).toList());
            }
        }
        return null;
    }

    /**
     * 주문 상품 옵션 정보 조회
     * @param dto
     * @return
     * @throws Exception
     */
    public List<OrderItemResponse> selectOrderItemList(OrderDto dto) throws Exception {
        return orderRepository.selectOrderItemList(dto);
    }

    /**
     * 주문 정보 저장
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean insertOrder(OrderDto dto) throws Exception {
        return orderRepository.insertOrder(dto);
    }

    /**
     * 주문 상태 변경
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean updateOrderStatus(OrderDto dto) throws Exception {
        return orderRepository.updateOrderStatus(dto);
    }

    /**
     * 주문 결제 상태 변경
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean updatePayStatus(OrderDto dto) throws Exception {
        return orderRepository.updatePayStatus(dto);
    }

}
