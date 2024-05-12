package com.flab.blackfriday.order.dto;

/**
 * packageName    : com.flab.blackfriday.order.dto
 * fileName       : PayStatusType
 * author         : rhkdg
 * date           : 2024-04-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-18        rhkdg       최초 생성
 */
public enum PayStatusType {

    WAIT("결제대기"),
    OK("결제완료"),
    CANCEL("결제취소");
    private String display = "";

    PayStatusType(String display){
        this.display = display;
    }

    public void setDisplay(String display){
        this.display = display;
    }

    public String getDisplay(){
        return this.display;
    }
}
