package com.flab.blackfriday.modules.order.payment.service;

import com.flab.blackfriday.auth.member.dto.MemberSession;
import com.flab.blackfriday.modules.order.payment.util.PaymentWebClientUtil;
import com.flab.blackfriday.modules.order.dto.OrderDto;
import com.flab.blackfriday.modules.order.dto.OrderStatusType;
import com.flab.blackfriday.modules.order.dto.PayStatusType;
import com.flab.blackfriday.modules.order.payment.dto.PaymentResponse;
import com.flab.blackfriday.modules.order.payment.exception.PaymentFailException;
import com.flab.blackfriday.modules.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        String apiUrl = "/mocks/pay/card/"+orderDto.getPrice();

        try {
            if(orderDto == null){
                throw new PaymentFailException("잘못된 접근입니다.");
            }
            if (!orderDto.getId().equals(memberSession.getMemberSession().getId())) {
                throw new PaymentFailException("회원정보가 일치하지 않습니다.");
            }

            System.out.println("payment url : "+payUrl+apiUrl);
            //결제 정보 response
            PaymentResponse paymentResponse = PaymentWebClientUtil.sendPayment(payUrl,apiUrl);
            System.out.println(paymentResponse.getResult().get("msg"));

            assert paymentResponse != null;
            if(paymentResponse.getResult().get("status").equals("200")){
                orderDto.setOrderStatus(OrderStatusType.OK.name());
                orderDto.setPayStatus(PayStatusType.OK.name());
                //주문 상태 변경
                orderService.updateOrderStatus(orderDto);
                //결제 상태 변경
                orderService.updatePayStatus(orderDto);
            //결제 실패할 경우
            }else if(paymentResponse.getResult().get("status").equals("00")){
                throw new PaymentFailException((String) paymentResponse.getResult().get("msg"));
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

        String apiUrl = "/mocks/cancel/card/"+orderDto.getPrice();

        try{
            if(orderDto == null){
                throw new PaymentFailException("회원정보가 일치하지 않습니다.");
            }
            if (!orderDto.getId().equals(memberSession.getMemberSession().getId())) {
                throw new PaymentFailException("회원정보가 일치하지 않습니다.");
            }

            System.out.println("payment url : "+payUrl+apiUrl);

            //결제 정보 response
            PaymentResponse paymentResponse = PaymentWebClientUtil.sendPayment(payUrl,apiUrl);
            System.out.println(paymentResponse.getResult().get("msg"));

            assert paymentResponse != null;
            if(paymentResponse.getResult().get("status").equals("200")){
                orderDto.setOrderStatus(OrderStatusType.CANCEL.name());
                orderDto.setPayStatus(PayStatusType.CANCEL.name());
                //주문 상태 변경
                orderService.updateOrderStatus(orderDto);
                //결제 상태 변경
                orderService.updatePayStatus(orderDto);
            }else if(paymentResponse.getResult().get("status").equals("00")){
                throw new PaymentFailException((String) paymentResponse.getResult().get("msg"));
            }
        }catch (Exception e){
            throw new PaymentFailException(e.getMessage());
        }
    }

}
