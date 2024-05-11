package com.flab.blackfriday.order.service;

import com.flab.blackfriday.common.dto.ResultVO;
import com.flab.blackfriday.order.domain.Order;
import com.flab.blackfriday.order.dto.*;
import com.flab.blackfriday.order.exception.OrderValidatorException;
import com.flab.blackfriday.order.repository.OrderRepository;
import com.flab.blackfriday.product.dto.ProductDto;
import com.flab.blackfriday.product.dto.ProductItemDto;
import com.flab.blackfriday.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    //주문 repository
    private final OrderRepository orderRepository;

    //상품 service
    private final ProductService productService;

    private final Lock lock = new ReentrantLock();

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
    public void insertOrder(OrderDto dto) throws Exception {
        lock.lock();
        try{
            ResultVO resultVO = checkOrderValidator(dto);
            if(resultVO.getStatusCode().equals("OK")) {
                orderRepository.insertOrder(dto);
            }else{
                throw new OrderValidatorException(resultVO.getMessage());
            }
        }finally {
            lock.unlock();
        }
    }

    /**
     * 상품 유효성 체크
     * @param orderDto
     * @return
     * @throws Exception
     */
    private ResultVO checkOrderValidator(OrderDto orderDto) throws Exception{
        
        ProductDto productDto = new ProductDto();
        productDto.setPNum(orderDto.getPNum());
        productDto = productService.selectProduct(productDto);

        //상품 사용여부 체크
        if(productDto != null ){
            if(productDto.getUseYn().equals("N")) {
                return new ResultVO("FAIL", "중지된 상품 상품입니다.");
            }
        }else {
            return new ResultVO("FAIL", "존재하지 않는 상품입니다.");
        }

        //상품 유효성 체크
        if(!productDto.getItemList().isEmpty()){
            for(OrderItemDto itemDto : orderDto.getItemList()) {
                for (ProductItemDto pItemDto : productDto.getItemList()) {
                    if (Objects.equals(itemDto.getPitmIdx(), pItemDto.getIdx())){
                        //개수가 작을 경우
                        if(itemDto.getPCnt() > pItemDto.getPItmCnt()){
                            return new ResultVO("FAIL","신청하신 상품의 개수가 부족합니다.");
                        }
                        //상품 금액 체크
                        if((itemDto.getPCnt()*itemDto.getPrice()) != (itemDto.getPCnt() * pItemDto.getPItmPrice())){
                            return new ResultVO("FAIL","신청하신 상품의 개수가 부족합니다.");
                        }
                    }
                }
            }
        }
        return new ResultVO("OK");
    }

    /**
     * 주문 상태 변경
     *
     * @param dto
     * @throws Exception
     */
    @Transactional
    public void updateOrderStatus(OrderDto dto) throws Exception {
        orderRepository.updateOrderStatus(dto);
    }

    /**
     * 주문 결제 상태 변경
     *
     * @param dto
     * @throws Exception
     */
    @Transactional
    public void updatePayStatus(OrderDto dto) throws Exception {
        orderRepository.updatePayStatus(dto);
    }

}