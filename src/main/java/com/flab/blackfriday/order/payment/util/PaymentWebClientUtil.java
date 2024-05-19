package com.flab.blackfriday.order.payment.util;

import com.flab.blackfriday.order.payment.dto.PaymentResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

/**
 * packageName    : com.flab.blackfriday.order.payment.util
 * fileName       : PaymentWebClientUtil
 * author         : rhkdg
 * date           : 2024-05-12
 * description    : 결제 외부 처리 api util
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-12        rhkdg       최초 생성
 */
public class PaymentWebClientUtil {

    /**
     * 외부 api 호출
     * @param apiUrl
     * @param requestMap
     * @return
     */
    public static PaymentResponse sendPayment(String apiUrl, Map<String,Object> requestMap) {
        //결제 외부 api 호출
        WebClient webClient = WebClient.builder().baseUrl(apiUrl).build();
        return postWebclient(webClient,requestMap);
    }

    public static PaymentResponse sendPayment(String apiUrl, String uri) {
        //결제 외부 api 호출
        WebClient webClient = WebClient.builder().baseUrl(apiUrl).build();
        return postWebclientUri(webClient,uri);
    }

    /**
     * 외부 api response 결과
     * @param webClient
     * @param requestMap
     * @return
     */
    public static PaymentResponse postWebclient(WebClient webClient, Map<String,Object> requestMap) {
        return webClient.post().bodyValue(requestMap)
                .retrieve().bodyToMono(PaymentResponse.class)
                .block();
    }

    public static PaymentResponse postWebclientUri(WebClient webClient,String uri) {
        return webClient.post().uri(uri)
                .retrieve().bodyToMono(PaymentResponse.class)
                .block();
    }

    private static boolean support(Class<?> classDto)  {
        if(PaymentResponse.class.isAssignableFrom(classDto)){
            return true;
        }
        return false;
    }
}
