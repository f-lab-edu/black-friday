package com.flab.blackfriday.product.dto;

import com.flab.blackfriday.product.domain.ProductItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.product.dto
 * fileName       : ProductItem
 * author         : rhkdg
 * date           : 2024-04-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-18        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductItemDto {

    /**일련번호*/
    private Long idx = 0L;

    /**상품번호*/
    private String pNum = "";

    /**상품 이름*/
    private String pItmName = "";

    /**상품 옵션 가격*/
    private int pItmPrice = 0;

    /**상품 옵션 개수*/
    private int pItmCnt = 0;

    /**상품 비고*/
    private String pItmRemark = "";

    /**등록일자*/
    private LocalDateTime createDate;

    /**수정일자*/
    private LocalDateTime modifyDate;

    /**
     * dto -> entity
     * @return
     */
    public ProductItem toEntity() {
        return ProductItem.builder().dto(this).build();
    }
    
}
