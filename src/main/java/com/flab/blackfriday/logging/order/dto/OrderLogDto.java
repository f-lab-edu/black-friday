package com.flab.blackfriday.logging.order.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.logging.order.dto
 * fileName       : OrderLogDto
 * author         : rhkdg
 * date           : 2024-06-20
 * description    : 주문 log
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-20        rhkdg       최초 생성
 */
@Getter
@Setter
public class OrderLogDto {

    private Long idx = 0L;

    private Long parentIdx = 0L;

    private String orderData = "";

    private String orderStatus = "";

}
