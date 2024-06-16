package com.flab.blackfriday.modules.order.service;

import com.flab.blackfriday.common.dto.ResultVO;
import com.flab.blackfriday.modules.order.domain.Order;
import com.flab.blackfriday.modules.order.dto.*;
import com.flab.blackfriday.modules.order.exception.OrderValidatorException;
import com.flab.blackfriday.modules.order.repository.OrderRepository;
import com.flab.blackfriday.modules.product.domain.Product;
import com.flab.blackfriday.modules.product.dto.ProductBlackFridayDto;
import com.flab.blackfriday.modules.product.dto.ProductDefaultDto;
import com.flab.blackfriday.modules.product.dto.ProductDto;
import com.flab.blackfriday.modules.product.dto.ProductItemDto;
import com.flab.blackfriday.modules.product.repository.ProductRepository;
import com.flab.blackfriday.modules.product.service.ProductBlackFridayService;
import com.flab.blackfriday.modules.product.service.ProductService;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

    //상품 lock repository
    private final ProductRepository productRepository;

    //상품 할인 정보 service
    private final ProductBlackFridayService productBlackFridayService;

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
            return orderDto;
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
        try {
            Product product = productRepository.findById(dto.getPNum()).orElse(null);
            // 주문 관련 상품 유효성 체크
            ResultVO<List<ProductItemDto>> resultVO = checkOrderValidator(dto,product);
            if (resultVO.getStatusCode().equals("OK")) {
                dto.setOrderStatus(OrderStatusType.NONE.name());
                dto.setPayStatus(PayStatusType.WAIT.name());

                //할인율 적용
                this.orderPriceSale(dto);


                if(product == null){
                    throw new OrderValidatorException("존재하지 않는 상품입니다.");
                }

                Order order = dto.toEntity();
                order.addProduct(product);
                order = orderRepository.save(order);

                List<ProductItemDto> itemList = resultVO.getElement();
                for (OrderItemDto itemDto : dto.getItemList()) {
                    itemDto.setOIdx(order.getIdx());
                    System.out.println("### itemDto toString : "+itemDto.toString());
                    //주문 옵션 등록
                    orderRepository.insertOrderItem(itemDto);
                    for (ProductItemDto productItemDto : itemList) {
                        if (Objects.equals(itemDto.getPitmIdx(), productItemDto.getIdx())) {
                            productItemDto.minusItemCnt(itemDto.getPCnt());
                            //상품 옵션 개수 업데이트
                            productService.updateProductItemCnt(productItemDto);
                        }
                    }
                }
            } else {
                throw new OrderValidatorException(resultVO.getMessage());
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * lock 걸지 않음
     * @param dto
     * @throws Exception
     */
    @Transactional
    public void insertOrderNoLock(OrderDto dto) throws Exception {

        Product product = productRepository.findById(dto.getPNum()).orElse(null);
        // 주문 관련 상품 유효성 체크
        ResultVO<List<ProductItemDto>> resultVO = checkOrderValidator(dto,product);
        if (resultVO.getStatusCode().equals("OK")) {
            dto.setOrderStatus(OrderStatusType.NONE.name());
            dto.setPayStatus(PayStatusType.WAIT.name());

            //할인율 적용
            this.orderPriceSale(dto);

            Order order = dto.toEntity();
            order.addProduct(product);
            order = orderRepository.save(order);

            List<ProductItemDto> itemList = resultVO.getElement();
            for (OrderItemDto itemDto : dto.getItemList()) {
                itemDto.setOIdx(order.getIdx());
                System.out.println("### itemDto toString : "+itemDto.toString());
                //주문 옵션 등록
                orderRepository.insertOrderItem(itemDto);
                for (ProductItemDto productItemDto : itemList) {
                    if (Objects.equals(itemDto.getPitmIdx(), productItemDto.getIdx())) {
                        productItemDto.minusItemCnt(itemDto.getPCnt());
                        //상품 옵션 개수 업데이트
                        productService.updateProductItemCnt(productItemDto);
                    }
                }
            }
        } else {
            throw new OrderValidatorException(resultVO.getMessage());
        }
    }

    /**
     *  비관적 lock을 이용한 성능테스트 용
     * @param dto
     * @throws Exception
     */
    @Transactional
    public void insertOrderPessimisticLock(OrderDto dto) throws Exception {
        Product product = productRepository.selectProductPessimisticLock(dto.getPNum());
        // 주문 관련 상품 유효성 체크
        ResultVO<List<ProductItemDto>> resultVO = checkOrderValidator(dto,product);
        if (resultVO.getStatusCode().equals("OK")) {

            dto.setOrderStatus(OrderStatusType.NONE.name());
            dto.setPayStatus(PayStatusType.WAIT.name());

            //할인율 적용
            this.orderPriceSale(dto);
            Order order = dto.toEntity();
            order.addProduct(product);
            order = orderRepository.save(order);

            List<ProductItemDto> itemList = resultVO.getElement();
            for (OrderItemDto itemDto : dto.getItemList()) {
                itemDto.setOIdx(order.getIdx());
                System.out.println("### itemDto toString : "+itemDto.toString());
                //주문 옵션 등록
                orderRepository.insertOrderItem(itemDto);
                for (ProductItemDto productItemDto : itemList) {
                    if (Objects.equals(itemDto.getPitmIdx(), productItemDto.getIdx())) {
                        //상품 개수 제거
                        productItemDto.minusItemCnt(itemDto.getPCnt());
                        //상품 옵션 개수 업데이트
                        productService.updateProductItemCnt(productItemDto);
                    }
                }
            }
        } else {
            throw new OrderValidatorException(resultVO.getMessage());
        }
        //마지막에는 for update이기 떄문에 무조건 업데이트 진행
        productRepository.save(product);
    }

    /**
     *  낙관적 lock을 이용한 성능테스트 용
     * @param dto
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean insertOrderOptimisticLock(OrderDto dto , Product product) throws Exception {

        // 주문 관련 상품 유효성 체크
        ResultVO<List<ProductItemDto>> resultVO = checkOrderValidator(dto,product);
        if (resultVO.getStatusCode().equals("OK")) {
            dto.setOrderStatus(OrderStatusType.NONE.name());
            dto.setPayStatus(PayStatusType.WAIT.name());

            //할인율 적용
            this.orderPriceSale(dto);
            Order order = dto.toEntity();
            order.addProduct(product);
            order = orderRepository.save(order);

            List<ProductItemDto> itemList = resultVO.getElement();
            for (OrderItemDto itemDto : dto.getItemList()) {
                itemDto.setOIdx(order.getIdx());
                System.out.println("### itemDto toString : "+itemDto.toString());
                //주문 옵션 등록
                orderRepository.insertOrderItem(itemDto);
                for (ProductItemDto productItemDto : itemList) {
                    if (Objects.equals(itemDto.getPitmIdx(), productItemDto.getIdx())) {
                        productItemDto.minusItemCnt(itemDto.getPCnt());
                        //상품 옵션 개수 업데이트
                        productService.updateProductItemCnt(productItemDto);
                    }
                }
            }
        } else {
            throw new OrderValidatorException(resultVO.getMessage());
        }
        return true;
    }

    @Transactional
    public boolean insertOrderOptimisticNolimitLock(OrderDto dto) throws Exception {
        try{
            Product product = productRepository.selectProductoptimisticLock(dto.getPNum());
            // 주문 관련 상품 유효성 체크
            ResultVO<List<ProductItemDto>> resultVO = checkOrderValidator(dto,product);
            if (resultVO.getStatusCode().equals("OK")) {
                dto.setOrderStatus(OrderStatusType.NONE.name());
                dto.setPayStatus(PayStatusType.WAIT.name());

                //할인율 적용
                this.orderPriceSale(dto);
                Order order = dto.toEntity();
                order.addProduct(product);
                order = orderRepository.save(order);

                List<ProductItemDto> itemList = resultVO.getElement();
                for (OrderItemDto itemDto : dto.getItemList()) {
                    itemDto.setOIdx(order.getIdx());
                    System.out.println("### itemDto toString : "+itemDto.toString());
                    //주문 옵션 등록
                    orderRepository.insertOrderItem(itemDto);
                    for (ProductItemDto productItemDto : itemList) {
                        if (Objects.equals(itemDto.getPitmIdx(), productItemDto.getIdx())) {
                            productItemDto.minusItemCnt(itemDto.getPCnt());
                            //상품 옵션 개수 업데이트
                            productService.updateProductItemCnt(productItemDto);
                        }
                    }
                }
            } else {
                throw new OrderValidatorException(resultVO.getMessage());
            }
        }catch (OptimisticLockException e) {
            throw new Exception("버전이 일치하지 않습니다.");
        }
        return true;
    }

    @Transactional
    public boolean insertOrderCompareAndSet(OrderDto dto) throws Exception {

        Product product = productRepository.findById(dto.getPNum()).orElse(null);
        // 주문 관련 상품 유효성 체크
        ResultVO<List<ProductItemDto>> resultVO = checkOrderValidator(dto,product);
        if (resultVO.getStatusCode().equals("OK")) {
            dto.setOrderStatus(OrderStatusType.NONE.name());
            dto.setPayStatus(PayStatusType.WAIT.name());

            //할인율 적용
            this.orderPriceSale(dto);
            Order order = dto.toEntity();
            order.addProduct(product);
            order = orderRepository.save(order);

            List<ProductItemDto> itemList = resultVO.getElement();
            for (OrderItemDto itemDto : dto.getItemList()) {
                itemDto.setOIdx(order.getIdx());
                System.out.println("### itemDto toString : "+itemDto.toString());
                //주문 옵션 등록
                orderRepository.insertOrderItem(itemDto);
                for (ProductItemDto productItemDto : itemList) {
                    if (Objects.equals(itemDto.getPitmIdx(), productItemDto.getIdx())) {
                        //상품 옵션 개수 업데이트
                        int result = productRepository.updateProductItemPcntCompareAndSet(productItemDto.getIdx(),itemDto.getPCnt());
                        if(result == 0){
                            throw new OrderValidatorException("재고가 부족합니다. 죄송합니다.");
                        }
                    }
                }
            }
        } else {
            throw new OrderValidatorException(resultVO.getMessage());
        }

        return true;
    }

    /**
     * 상품 유효성 체크
     * @param orderDto
     * @return
     * @throws Exception
     */
    private ResultVO<List<ProductItemDto>> checkOrderValidator(OrderDto orderDto,Product product) throws Exception{

        Map<String,Object> map = new HashMap<>();
        ProductDto productDto = null;
        //상품 사용여부 체크
        if(product != null ){
            productDto = new ProductDto(product);
            if(productDto.getUseYn().equals("N")) {
                return new ResultVO<>("FAIL", "중지된 상품 상품입니다.");
            }
        }else {
            return new ResultVO<>("FAIL", "존재하지 않는 상품입니다.");
        }

        List<ProductItemDto> resultItemDto = new ArrayList<>();

        ProductDefaultDto searchDto = new ProductDefaultDto();
        searchDto.setPNum(productDto.getPNum());
        productDto.setItemList(productRepository.selectProductItemList(searchDto));

        //상품 유효성 체크
        if(!productDto.getItemList().isEmpty()){
            for(OrderItemDto itemDto : orderDto.getItemList()) {
                for (ProductItemDto pItemDto : productDto.getItemList()) {
                    if (Objects.equals(itemDto.getPitmIdx(), pItemDto.getIdx())){
                        //개수가 작을 경우
                        if(itemDto.getPCnt() > pItemDto.getPItmCnt()){
                            return new ResultVO<>("FAIL","신청하신 상품의 개수가 부족합니다.");
                        }
                        resultItemDto.add(pItemDto);
                    }
                }
            }
        }
        return new ResultVO<>("OK","정상",resultItemDto);
    }

    /**
     * 할인율 적용
     * @param orderDto
     */
    private void orderPriceSale(OrderDto orderDto) throws Exception {
        ProductBlackFridayDto productBlackFridayDto = new ProductBlackFridayDto();
        productBlackFridayDto.setPNum(orderDto.getPNum());
        productBlackFridayDto.setUseYn("Y");
        productBlackFridayDto = productBlackFridayService.selectProductBlackFriday(productBlackFridayDto);

        if(productBlackFridayDto != null) {
            if(productBlackFridayDto.getPNum().equals(orderDto.getPNum())){
                if(productBlackFridayDto.getSale() > 0) {
                    int price = orderDto.getPrice();
                    price = (int) (price * ((float)(100 - productBlackFridayDto.getSale())/100));
                    orderDto.setPrice(price);
                }
            }
        }

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
