package com.flab.blackfriday.order.payment.service;

import com.flab.blackfriday.order.dto.OrderDto;
import com.flab.blackfriday.order.payment.dto.PaymentResponse;
import com.flab.blackfriday.order.payment.exception.PaymentFailException;
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

    @Value("${postman.url}")
    private String payUrl;

    /**
     * 결제 처리
     * @param orderDto
     * @throws Exception
     */
    public void payment(OrderDto orderDto) throws Exception {

        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("type" , "card");

        String apiUrl = payUrl+"/postman/payment/v1/pay";

        try {
            //결제 외부 api 호출
            WebClient webClient = WebClient.builder().baseUrl(apiUrl).build();

            //결제 정보 response
            PaymentResponse paymentResponse = webClient.post().bodyValue(requestMap)
                    .retrieve().bodyToMono(PaymentResponse.class)
                    .block();

            assert paymentResponse != null;
            if(paymentResponse.getStatus().equals("200")){
                orderService.insertOrder(orderDto);
            }else{
                throw new PaymentFailException("결제되지 않았습니다.");
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new PaymentFailException(e.getMessage());
        }

    }

}
