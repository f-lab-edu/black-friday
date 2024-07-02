package com.flab.blackfriday.modules.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.product.dto
 * fileName       : ProductItemRequest
 * author         : GAMJA
 * date           : 2024/04/21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/21        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductItemRequest {

    /**일련번호*/
    private Long idx = 0L;

    /**상품번호*/
    private String pNum = "";

    /**상품 이름*/
    @NotNull(message = "상품 이름을 입력해주시기 바랍니다.")
    private String pItmName = "";

    /**상품 옵션 가격*/
    @NotNull(message = "상품 옵션 가격을 입력해주시기 바랍니다.")
    private int pItmPrice = 0;

    /**상품 옵션 개수*/
    @NotNull(message = "상품 옵션 개수를 입력해주시기 바랍니다.")
    private int pItmCnt = 0;

    /**상품 비고*/
    private String pItmRemark = "";


}
