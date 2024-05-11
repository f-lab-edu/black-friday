package com.flab.blackfriday.order.controller;

import com.flab.blackfriday.auth.member.dto.MemberSession;
import com.flab.blackfriday.common.controller.BaseModuleController;
import com.flab.blackfriday.common.dto.ResultVO;
import com.flab.blackfriday.common.exception.NoExistAuthException;
import com.flab.blackfriday.order.dto.OrderDefaultDto;
import com.flab.blackfriday.order.dto.OrderDto;
import com.flab.blackfriday.order.dto.OrderSummaryResponse;
import com.flab.blackfriday.order.dto.action.OrderCreateRequest;
import com.flab.blackfriday.order.payment.exception.PaymentFailException;
import com.flab.blackfriday.order.payment.service.PaymentService;
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

        try{
            if(!memberSession.isAuthenticated()){
                logger.error("### 인증되지 않은 접근. ### ");
                throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.",HttpStatus.UNAUTHORIZED.name());
            }

            OrderDto orderDto = OrderDto.orderOf(orderCreateRequest);
            orderDto.setId(memberSession.getMemberSession().getId());
            orderService.insertOrder(orderDto);
        }catch (Exception e) {
            logger.error("### insert order error : {}",e.getMessage());
            return new ResponseEntity<>("주문 요청 처리시 오류가 발생했습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok().body(new ResultVO("OK","주문신청되었습니다."));
    }

    /**
     * 결제 신청
     * @param idx
     * @return
     * @throws Exception
     */
    @PostMapping(API_URL+"/order/pay")
    public ResponseEntity<?> orderPayment(@RequestParam("idx") long idx) throws Exception {
        try{
            if(!memberSession.isAuthenticated()){
                logger.error("### 인증되지 않은 접근. ### ");
                throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.",HttpStatus.UNAUTHORIZED.name());
            }

            OrderDto orderDto = new OrderDto();
            orderDto.setIdx(idx);
            orderDto = orderService.selectOrder(orderDto);
            paymentService.payment(orderDto);
        }catch (Exception e) {
            logger.error("### order pay error : {}",e.getMessage());
            return new ResponseEntity<>("결제 처리시 오류가 발생했습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
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
        try{
            if(!memberSession.isAuthenticated()){
                logger.error("### 인증되지 않은 접근. ### ");
                throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.",HttpStatus.UNAUTHORIZED.name());
            }
            
            OrderDto orderDto = new OrderDto();
            orderDto.setIdx(idx);
            orderDto = orderService.selectOrder(orderDto);
            paymentService.cancel(orderDto);
        }catch (Exception e) {
            logger.error("### order pay cancel error : {}",e.getMessage());
            return new ResponseEntity<>("결제 취소 처리시 오류가 발생했습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return ResponseEntity.ok().body(new ResultVO("OK","취소되었습니다."));
    }
}
