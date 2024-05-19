package com.flab.blackfriday.order.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * packageName    : com.flab.blackfriday.order.payment.dto
 * fileName       : PaymentResponse
 * author         : GAMJA
 * date           : 2024/05/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/10        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private int amount = 0;

    private Map<String,Object> result;

}
