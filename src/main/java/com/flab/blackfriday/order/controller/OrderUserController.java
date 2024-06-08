package com.flab.blackfriday.order.controller;

import com.flab.blackfriday.auth.member.dto.MemberSession;
import com.flab.blackfriday.common.controller.BaseModuleController;
import com.flab.blackfriday.common.dto.ResultVO;
import com.flab.blackfriday.common.exception.NoExistAuthException;
import com.flab.blackfriday.common.response.CommonResponse;
import com.flab.blackfriday.order.dto.OrderDefaultDto;
import com.flab.blackfriday.order.dto.OrderDto;
import com.flab.blackfriday.order.dto.OrderSummaryResponse;
import com.flab.blackfriday.order.dto.action.OrderCreateRequest;
import com.flab.blackfriday.order.payment.exception.PaymentFailException;
import com.flab.blackfriday.order.payment.service.PaymentService;
import com.flab.blackfriday.order.service.OrderLockService;
import com.flab.blackfriday.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.coyote.Response;
import org.springframework.dao.UncategorizedDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : com.flab.blackfriday.order.controller
 * fileName       : OrderUserController
 * author         : GAMJA
 * date           : 2024/05/10
 * description    : 주문 하는 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/10        GAMJA       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class OrderUserController extends BaseModuleController {

    private final OrderService orderService;

    private final OrderLockService orderLockService;

    private final PaymentService paymentService;

    private final MemberSession memberSession;


    /**
     * 주문 목록
     * @param orderDefaultDto
     * @return
     * @throws Exception
     */
    @GetMapping(API_URL+"/order/list")
    public Page<OrderSummaryResponse> selectOrderPageList(OrderDefaultDto orderDefaultDto) throws Exception {
        if(!memberSession.isAuthenticated()){
            logger.error("### 인증되지 않은 접근. ### ");
            throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.",HttpStatus.UNAUTHORIZED.name());
        }
        return orderService.selectOrderPageList(orderDefaultDto);
    }

    /**
     * 주문 상세 정보 조회
     * @param idx
     * @return
     * @throws Exception
     */
    @GetMapping(API_URL+"/order/{idx}")
    public OrderDto selectOrderView(@PathVariable("idx") long idx) throws Exception {
        if(!memberSession.isAuthenticated()){
            logger.error("### 인증되지 않은 접근. ### ");
            throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.",HttpStatus.UNAUTHORIZED.name());
        }
        OrderDto orderDto = new OrderDto();
        orderDto = orderService.selectOrder(orderDto);
        if(orderDto == null || !orderDto.getId().equals(memberSession.getMemberSession().getId())){
            throw new NoExistAuthException("해당 정보가 존재하지 않습니다.",HttpStatus.UNAUTHORIZED.name());
        }
        return orderDto;
    }

    /**
     * 주문 처리
     * @return
     * @throws Exception
     */
    @PostMapping(API_URL+"/order")
    public ResponseEntity<?> insertOrder(final @Valid @RequestBody OrderCreateRequest orderCreateRequest) throws Exception {
        try {
            if (!memberSession.isAuthenticated()) {
                logger.error("### 인증되지 않은 접근. ### ");
                throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.", HttpStatus.UNAUTHORIZED.name());
            }

            System.out.println("#### create tostring : " + orderCreateRequest.toString());

            OrderDto orderDto = OrderDto.orderOf(orderCreateRequest);
            orderDto.setId(memberSession.getMemberSession().getId());
            orderService.insertOrder(orderDto);
            paymentService.payment(orderDto);
        } catch (Exception e) {
            logger.error("#### order pay error : {}", e.getMessage());
            return new ResponseEntity<>(new CommonResponse("주문시 오류가 발생했습니다.", null), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return ResponseEntity.ok().body(new ResultVO<>("OK","주문신청되었습니다."));
    }

    /**
     * 주문 상품 재고 적합성 제거한 내용
     * @param orderCreateRequest
     * @return
     * @throws Exception
     */
    @PostMapping(API_URL+"/order/no/lock")
    public ResponseEntity<?> insertOrderNoLock(final @Valid @RequestBody OrderCreateRequest orderCreateRequest) throws Exception {
        try {
            if (!memberSession.isAuthenticated()) {
                logger.error("### 인증되지 않은 접근. ### ");
                throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.", HttpStatus.UNAUTHORIZED.name());
            }

            System.out.println("#### create tostring : " + orderCreateRequest.toString());

            OrderDto orderDto = OrderDto.orderOf(orderCreateRequest);
            orderDto.setId(memberSession.getMemberSession().getId());
            orderService.insertOrderNoLock(orderDto);
            paymentService.payment(orderDto);
        } catch (Exception e) {
            logger.error("#### order pay error : {}", e.getMessage());
            return new ResponseEntity<>(new CommonResponse("주문시 오류가 발생했습니다.", null), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return ResponseEntity.ok().body(new ResultVO<>("OK","주문신청되었습니다."));
    }

    /**
     * 주문 처리 비관적 lock 테스트
     * @param orderCreateRequest
     * @return
     * @throws Exception
     */
    @PostMapping(API_URL+"/order/lock/v1")
    public ResponseEntity<?> insertOrderLockv1(final @Valid @RequestBody OrderCreateRequest orderCreateRequest) throws Exception {

        try {
            if (!memberSession.isAuthenticated()) {
                logger.error("### 인증되지 않은 접근. ### ");
                throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.", HttpStatus.UNAUTHORIZED.name());
            }

            System.out.println("#### create tostring : " + orderCreateRequest.toString());

            OrderDto orderDto = OrderDto.orderOf(orderCreateRequest);
            orderDto.setId(memberSession.getMemberSession().getId());
            orderService.insertOrderPessimisticLock(orderDto);
            paymentService.payment(orderDto);
        }catch (Exception e) {
            logger.error("#### order pay error : {}",e.getMessage());
            return new ResponseEntity<>(new CommonResponse("주문시 오류가 발생했습니다.",null),HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok().body(new ResultVO<>("OK","주문신청되었습니다."));
    }

    /**
     * 주문 처리 낙관적 lock 테스트
     * @param orderCreateRequest
     * @return
     * @throws Exception
     */
    @PostMapping(API_URL+"/order/lock/v2")
    public ResponseEntity<?> insertOrderLockv2(final @Valid @RequestBody OrderCreateRequest orderCreateRequest) throws Exception {

        try {
            if(!memberSession.isAuthenticated()){
                logger.error("### 인증되지 않은 접근. ### ");
                throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.",HttpStatus.UNAUTHORIZED.name());
            }

            System.out.println("#### create tostring : "+orderCreateRequest.toString());

            OrderDto orderDto = OrderDto.orderOf(orderCreateRequest);
            orderDto.setId(memberSession.getMemberSession().getId());
            orderLockService.insertOrderOptimisticLock(orderDto);
            paymentService.payment(orderDto);
        }catch (Exception e) {
            logger.error("#### order pay error : {}",e.getMessage());
            return new ResponseEntity<>(new CommonResponse("주문시 오류가 발생했습니다.",null),HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok().body(new ResultVO<>("OK","주문신청되었습니다."));
    }

    @PostMapping(API_URL+"/order/lock/v3")
    public ResponseEntity<?> insertOrderLockv3(final @Valid @RequestBody OrderCreateRequest orderCreateRequest) throws Exception {

        try {
            if(!memberSession.isAuthenticated()){
                logger.error("### 인증되지 않은 접근. ### ");
                throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.",HttpStatus.UNAUTHORIZED.name());
            }

            System.out.println("#### create tostring : "+orderCreateRequest.toString());

            OrderDto orderDto = OrderDto.orderOf(orderCreateRequest);
            orderDto.setId(memberSession.getMemberSession().getId());
            orderLockService.insertOrderOptimisticLockAsync(orderDto);
            paymentService.payment(orderDto);
        }catch (Exception e) {
            logger.error("#### order pay error : {}",e.getMessage());
            return new ResponseEntity<>(new CommonResponse("주문시 오류가 발생했습니다.",null),HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok().body(new ResultVO<>("OK","주문신청되었습니다."));
    }

    @PostMapping(API_URL+"/order/lock/v4")
    public ResponseEntity<?> insertOrderLockv4(final @Valid @RequestBody OrderCreateRequest orderCreateRequest) throws Exception {

        try {
            if(!memberSession.isAuthenticated()){
                logger.error("### 인증되지 않은 접근. ### ");
                throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.",HttpStatus.UNAUTHORIZED.name());
            }

            System.out.println("#### create tostring : "+orderCreateRequest.toString());

            OrderDto orderDto = OrderDto.orderOf(orderCreateRequest);
            orderDto.setId(memberSession.getMemberSession().getId());
            orderLockService.insertOrderOptimisticLockAsyncCache(orderDto);
            paymentService.payment(orderDto);
        }catch (Exception e) {
            logger.error("#### order pay error : {}",e.getMessage());
            return new ResponseEntity<>(new CommonResponse("주문시 오류가 발생했습니다.",null),HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok().body(new ResultVO<>("OK","주문신청되었습니다."));
    }

    @PostMapping(API_URL+"/order/lock/v5")
    public ResponseEntity<?> insertOrderLockv5(final @Valid @RequestBody OrderCreateRequest orderCreateRequest) throws Exception {

        try {
            if(!memberSession.isAuthenticated()){
                logger.error("### 인증되지 않은 접근. ### ");
                throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.",HttpStatus.UNAUTHORIZED.name());
            }

            System.out.println("#### create tostring : "+orderCreateRequest.toString());

            OrderDto orderDto = OrderDto.orderOf(orderCreateRequest);
            orderDto.setId(memberSession.getMemberSession().getId());
            orderLockService.insertOrderOptimisticLockAsyncCacheThread(orderDto);
            paymentService.payment(orderDto);
        }catch (Exception e) {
            logger.error("#### order pay error : {}",e.getMessage());
            return new ResponseEntity<>(new CommonResponse("주문시 오류가 발생했습니다.",null),HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok().body(new ResultVO<>("OK","주문신청되었습니다."));
    }

    @PostMapping(API_URL+"/order/lock/v6")
    public ResponseEntity<?> insertOrderLockv6(final @Valid @RequestBody OrderCreateRequest orderCreateRequest) throws Exception {

        try {
            if(!memberSession.isAuthenticated()){
                logger.error("### 인증되지 않은 접근. ### ");
                throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.",HttpStatus.UNAUTHORIZED.name());
            }

            System.out.println("#### create tostring : "+orderCreateRequest.toString());

            OrderDto orderDto = OrderDto.orderOf(orderCreateRequest);
            orderDto.setId(memberSession.getMemberSession().getId());
            orderLockService.insertOrderOptimisticLockAsyncCacheNoLimit(orderDto);
            paymentService.payment(orderDto);
        }catch (Exception e) {
            logger.error("#### order pay error : {}",e.getMessage());
            return new ResponseEntity<>(new CommonResponse("주문시 오류가 발생했습니다.",null),HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok().body(new ResultVO<>("OK","주문신청되었습니다."));
    }

    @PostMapping(API_URL+"/order/lock/v7")
    public ResponseEntity<?> insertOrderLockv7(final @Valid @RequestBody OrderCreateRequest orderCreateRequest) throws Exception {

        try {
            if(!memberSession.isAuthenticated()){
                logger.error("### 인증되지 않은 접근. ### ");
                throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.",HttpStatus.UNAUTHORIZED.name());
            }

            System.out.println("#### create tostring : "+orderCreateRequest.toString());

            OrderDto orderDto = OrderDto.orderOf(orderCreateRequest);
            orderDto.setId(memberSession.getMemberSession().getId());
            orderLockService.insertOrderOptimisticLockAsyncCacheNoLimitv2(orderDto);
            paymentService.payment(orderDto);
        }catch (Exception e) {
            logger.error("#### order pay error : {}",e.getMessage());
            return new ResponseEntity<>(new CommonResponse("주문시 오류가 발생했습니다.",null),HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok().body(new ResultVO<>("OK","주문신청되었습니다."));
    }

    /**
     * 결제 신청
     * @param idx
     * @return
     * @throws Exception
     */
    @PostMapping(API_URL+"/order/pay")
    public ResponseEntity<?> orderPayment(@RequestParam("idx") long idx) throws Exception {

        if(!memberSession.isAuthenticated()){
            logger.error("### 인증되지 않은 접근. ### ");
            throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.",HttpStatus.UNAUTHORIZED.name());
        }

        OrderDto orderDto = new OrderDto();
        orderDto.setIdx(idx);
        orderDto = orderService.selectOrder(orderDto);

        if(orderDto != null){
            paymentService.payment(orderDto);
        }else {
            throw new NoExistAuthException("주문 정보가 존재하지 않습니다.",HttpStatus.UNAUTHORIZED.name());
        }

        return ResponseEntity.ok().body(new ResultVO("OK","결제되었습니다."));
    }

    /**
     * 결제 취소 요청
     * @param idx
     * @return
     * @throws Exception
     */
    @PutMapping(API_URL+"/order/cancel")
    public ResponseEntity<?> orderCancelPayment(@RequestParam("idx") long idx) throws Exception {

        if(!memberSession.isAuthenticated()){
            logger.error("### 인증되지 않은 접근. ### ");
            throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.",HttpStatus.UNAUTHORIZED.name());
        }

        OrderDto orderDto = new OrderDto();
        orderDto.setIdx(idx);
        orderDto = orderService.selectOrder(orderDto);

        if(orderDto != null){
            paymentService.cancel(orderDto);
        }else {
            throw new NoExistAuthException("주문 정보가 존재하지 않습니다.",HttpStatus.UNAUTHORIZED.name());
        }

        return ResponseEntity.ok().body(new ResultVO("OK","취소되었습니다."));
    }
}
