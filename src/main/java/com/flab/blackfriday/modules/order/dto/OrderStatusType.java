package com.flab.blackfriday.modules.order.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.order.dto
 * fileName       : OrderStatusType
 * author         : rhkdg
 * date           : 2024-04-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-18        rhkdg       최초 생성
 */
public enum OrderStatusType {

    NONE("주문접수"),
    OK("구매완료"),
    CANCEL("구매취소");

    private String display = "";

    OrderStatusType(String display){
        this.display = display;
    }

    public void setDisplay(String display){
        this.display = display;
    }

    public String getDisplay(){
        return this.display;
    }
}
