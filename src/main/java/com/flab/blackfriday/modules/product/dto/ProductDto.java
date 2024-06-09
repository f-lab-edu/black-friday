package com.flab.blackfriday.modules.product.dto;

import com.flab.blackfriday.modules.product.domain.Product;
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

    /**카테고리 명칭*/
    private String categNm = "";

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
    
    /**인기 상품 여부*/
    private String populYn;

    /**블렉프라이데이 설정 정보*/
    private ProductBlackFridayDto productBlackFridayDto = new ProductBlackFridayDto();

    /**옵션 정보*/
    private List<ProductItemDto> itemList = new ArrayList<>();

    /**
     * dto -> entity
     * @return
     */
    public Product toEntity() {
        return Product.builder().dto(this).build();
    }

    public ProductDto (Product product) {
        this.pNum = product.getPNum();
        this.categCd = product.getCategory().getCategCd();
        this.categNm = product.getCategory().getCategNm();
        this.pTitle = product.getPTitle();
        this.pContent = product.getPContent();
        this.ord = product.getOrd();
        this.useYn = product.getUseYn();
        this.populYn = product.getPopulYn();
        this.createDate = product.getCreateDate();
        this.modifyDate = product.getModifyDate();
    }

    public ProductDto (String pNum, String categCd, String categNm, String pTitle,
                       String pContent, int ord, String useYn, LocalDateTime createDate, LocalDateTime modifyDate){
        this.pNum = pNum;
        this.categCd = categCd;
        this.categNm = categNm;
        this.pTitle = pTitle;
        this.pContent = pContent;
        this.ord = ord;
        this.useYn = useYn;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }

    public static ProductDto requestOf(ProductRequest productRequest) {
        ProductDto dto = new ProductDto();
        dto.setPNum(productRequest.getPNum());
        dto.setPTitle(productRequest.getPTitle());
        dto.setCategCd(productRequest.getCategCd());
        dto.setPContent(productRequest.getPContent());
        dto.setUseYn(productRequest.getUseYn());
        for(ProductItemRequest item : productRequest.getItemList()){
            ProductItemDto itemDto = new ProductItemDto();
            itemDto.setIdx(item.getIdx());
            itemDto.setPNum(dto.getPNum());
            itemDto.setPItmName(item.getPItmName());
            itemDto.setPItmCnt(item.getPItmCnt());
            itemDto.setPItmRemark(item.getPItmRemark());
            dto.getItemList().add(itemDto);
        }
        return dto;
    }
    
}
