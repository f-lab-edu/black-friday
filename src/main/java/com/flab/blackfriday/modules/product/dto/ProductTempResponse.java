package com.flab.blackfriday.modules.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.product.dto
 * fileName       : ProductResponse
 * author         : rhkdg
 * date           : 2024-05-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-25        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductTempResponse {

    private String pNum = "";

    private long pitmIdx = 0;

    private int pCnt = 0;

    private int price = 0;


}
