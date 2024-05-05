package com.flab.blackfriday.category.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.category.dto
 * fileName       : CategoryRequest
 * author         : GAMJA
 * date           : 2024/04/19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/19        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class CategoryRequest {

    /**카테고리 코드*/
    @NotNull(message = "카테고리 코드는 필수입력입니다.")
    private String categCd = "";

    /**부모 코드*/
    @NotNull(message = "부모 카테고리 코드는 필수입력입니다.")
    private String parentCd = "";

    /**카테고리 명*/
    @NotNull(message = "카테코리 명칭은 필수입력입니다.")
    private String categNm = "";

    /**정렬*/
    private int ord = 0;

    /**사용유무*/
    @NotNull(message = "사용유무는 필수입력입니다. (사용함, 사용안함 중 선택해주시기 바랍니다.)")
    private String useYn = "";

}
