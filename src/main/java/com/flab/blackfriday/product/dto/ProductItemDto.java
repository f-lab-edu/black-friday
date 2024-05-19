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

    public ProductItemDto (ProductItem entity){
        this.idx=  entity.getIdx();
        this.pNum = entity.getProduct().getPNum();
        this.pItmName = entity.getPItmName();
        this.pItmPrice = entity.getPItmPrice();
        this.pItmCnt = entity.getPItmCnt();
        this.pItmRemark = entity.getPItmRemark();
        this.createDate = entity.getCreateDate();
        this.modifyDate = entity.getModifyDate();
    }

    /**
     * 개수 +
     * @param cnt
     */
    public void plusItemCnt(int cnt) {
        this.pItmCnt += cnt;
    }

    /**
     * 개수 - 처리
     * @param cnt
     */
    public void minusItemCnt(int cnt) {
        this.pItmCnt -= cnt;
    }
}
