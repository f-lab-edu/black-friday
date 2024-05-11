package com.flab.blackfriday.order.controller;

import com.flab.blackfriday.auth.member.dto.MemberSession;
import com.flab.blackfriday.common.controller.BaseModuleController;
import com.flab.blackfriday.common.dto.ResultVO;
import com.flab.blackfriday.order.dto.OrderDto;
import com.flab.blackfriday.order.dto.action.OrderCreateRequest;
import com.flab.blackfriday.order.payment.service.PaymentService;
import com.flab.blackfriday.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.dao.UncategorizedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * 주문 처리
     * @return
     * @throws Exception
     */
    @PostMapping(API_URL+"/order")
    public ResponseEntity<?> insertOrder(final @Valid @RequestBody OrderCreateRequest orderCreateRequest) throws Exception {

        try{
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
            OrderDto orderDto = new OrderDto();
            orderDto.setIdx(idx);
            orderDto = orderService.selectOrder(orderDto);
            paymentService.payment(orderDto);
        }catch (Exception e) {

        }
        return null;
    } 
}
