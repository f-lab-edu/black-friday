package com.flab.blackfriday.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : com.flab.blackfriday.product.dto
 * fileName       : ProductRequest
 * author         : GAMJA
 * date           : 2024/04/21
 * description    : 상품 등록 request
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/21        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {

    private String pNum = "";

    @NotNull(message = "카테고리는 필수입력입니다.")
    private String categCd = "";

    @NotNull(message = "상품 제목은 필수입력입니다. ")
    private String pTitle = "";

    @NotNull(message = "상품 상세설명은 필수입력입니다.")
    private String pContent = "";

    @NotBlank(message = "사용여부는 사용함 또는 사용안함을 선택해주시기 바랍니다.")
    private String useYn = "";

    /**
     * 상품 옵션 목록
     */
    private List<ProductItemRequest> itemList = new ArrayList<>();

}
