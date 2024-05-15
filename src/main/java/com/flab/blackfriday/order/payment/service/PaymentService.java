package com.flab.blackfriday.order.payment.service;

import com.flab.blackfriday.auth.member.dto.MemberSession;
import com.flab.blackfriday.order.dto.OrderDto;
import com.flab.blackfriday.order.dto.OrderStatusType;
import com.flab.blackfriday.order.dto.PayStatusType;
import com.flab.blackfriday.order.payment.dto.PaymentResponse;
import com.flab.blackfriday.order.payment.exception.PaymentFailException;
import com.flab.blackfriday.order.payment.util.PaymentWebClientUtil;
import com.flab.blackfriday.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.flab.blackfriday.order.payment.service
 * fileName       : PaymentService
 * author         : GAMJA
 * date           : 2024/05/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/10        GAMJA       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final OrderService orderService;

    @Value("${mocks_server.url}")
    private String payUrl;

    private final MemberSession memberSession;

    /**
     * 결제 처리
     * @param orderDto
     * @throws Exception
     */
    @Transactional
    public void payment(OrderDto orderDto) throws Exception {

        Map<String,Object> requestMap = new HashMap<>();

        String apiUrl = payUrl+"/mocks/pay/card/"+orderDto.getPrice();

        try {
            if(orderDto == null){
                throw new PaymentFailException("잘못된 접근입니다.");
            }
            if (!orderDto.getId().equals(memberSession.getMemberSession().getId())) {
                throw new PaymentFailException("회원정보가 일치하지 않습니다.");
            }
            //결제 정보 response
            PaymentResponse paymentResponse = PaymentWebClientUtil.sendPayment(apiUrl,requestMap);

            assert paymentResponse != null;
            if(paymentResponse.getStatus().equals("200")){
                orderDto.setOrderStatus(OrderStatusType.OK.name());
                orderDto.setPayStatus(PayStatusType.OK.name());
                //주문 상태 변경
                orderService.updateOrderStatus(orderDto);
                //결제 상태 변경
                orderService.updatePayStatus(orderDto);
            //결제 실패할 경우
            }else if(paymentResponse.getStatus().equals("00")){
                throw new PaymentFailException(paymentResponse.getText());
            }
        }catch (Exception e) {
            throw new PaymentFailException(e.getMessage());
        }

    }

    /**
     * 주문 취소 (환불 개념이 포함된 내용)
     * @param orderDto
     * @throws Exception
     */
    @Transactional
    public void cancel(OrderDto orderDto) throws Exception {

        Map<String,Object> requestMap = new HashMap<>();

        String apiUrl = payUrl+"/mocks/cancel/card/"+orderDto.getPrice();

        try{
            if(orderDto == null){
                throw new PaymentFailException("회원정보가 일치하지 않습니다.");
            }
            if (!orderDto.getId().equals(memberSession.getMemberSession().getId())) {
                throw new PaymentFailException("회원정보가 일치하지 않습니다.");
            }
            //결제 정보 response
            PaymentResponse paymentResponse = PaymentWebClientUtil.sendPayment(apiUrl,requestMap);

            assert paymentResponse != null;
            if(paymentResponse.getStatus().equals("200")){
                orderDto.setOrderStatus(OrderStatusType.CANCEL.name());
                orderDto.setPayStatus(PayStatusType.CANCEL.name());
                //주문 상태 변경
                orderService.updateOrderStatus(orderDto);
                //결제 상태 변경
                orderService.updatePayStatus(orderDto);
            }else if(paymentResponse.getStatus().equals("00")){
                throw new PaymentFailException(paymentResponse.getText());
            }
        }catch (Exception e){
            throw new PaymentFailException(e.getMessage());
        }
    }

}
