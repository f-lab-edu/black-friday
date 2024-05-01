package com.flab.blackfriday.product.dto;

import com.flab.blackfriday.product.domain.ProductBlackFriday;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.product.dto
 * fileName       : ProductBlackFridayDto
 * author         : rhkdg
 * date           : 2024-04-18
 * description    : 블랙 프라이데이 설정 Dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-18        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBlackFridayDto {
    
    /**일련번호*/
    private Long idx = 0L;
    
    /**상품번호*/
    private String pNum = "";
    
    /**할인율*/
    private int sale = 0;
    
    /**사용유무*/
    private String useYn = "";
    
    /**등록일자*/
    private LocalDateTime createDate;
    
    /**수정일자*/
    private LocalDateTime modifyDate;

    public ProductBlackFriday toEntity() {
        return ProductBlackFriday.builder().dto(this).build();
    }

    public ProductBlackFridayDto(ProductBlackFriday entity){
        this.idx = entity.getIdx();
        this.pNum = entity.getProduct().getPNum();
        this.sale = entity.getSale();
        this.useYn = entity.getUseYn();
        this.createDate = entity.getCreateDate();
        this.modifyDate = entity.getModifyDate();
    }
}
