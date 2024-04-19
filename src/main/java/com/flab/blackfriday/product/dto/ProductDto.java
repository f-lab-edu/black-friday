package com.flab.blackfriday.product.dto;

import com.flab.blackfriday.product.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : com.flab.blackfriday.product.dto
 * fileName       : ProductDto
 * author         : rhkdg
 * date           : 2024-04-17
 * description    : 상품 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-17        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
    
    /**상품번호*/
    private String pNum;

    /**카테고리 코드*/
    private String categCd = "";

    /**상품 제목*/
    private String pTitle = "";

    /**상품 상세 내용*/
    private String pContent = "";

    /** 정렬 순서*/
    private int ord = 0;

    /** 사용유무*/
    private String useYn = "";

    /**등록 일자*/
    private LocalDateTime createDate;

    /**수정 일자*/
    private LocalDateTime modifyDate;

    private List<ProductItemDto> itemList = new ArrayList<>();

    /**
     * dto -> entity
     * @return
     */
    public Product toEntity() {
        return Product.builder().dto(this).build();
    }
    
}
