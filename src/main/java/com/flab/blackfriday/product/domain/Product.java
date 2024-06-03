package com.flab.blackfriday.product.domain;

import com.flab.blackfriday.category.domain.Category;
import com.flab.blackfriday.product.dto.ProductDto;
import com.flab.blackfriday.product.dto.ProductItemDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : com.flab.blackfriday.product.domain
 * fileName       : Product
 * author         : rhkdg
 * date           : 2024-04-17
 * description    : 상품 entity
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-17        rhkdg       최초 생성
 */
@Entity
@Table(name="product")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product implements Persistable<String> {

    @Id
    @Comment("상품번호")
    private String pNum; //상품번호

    @ManyToOne
    @JoinColumn(name="categ_cd",nullable = false)
    private Category category;

    @Comment("상품제목")
    @Column(length = 200)
    private String pTitle; // 상품 제목

    @Comment("상품 상세내용")
    @Lob
    private String pContent; // 상품 상세내용

    @Comment("정렬순서")
    private int ord =0; //정렬 순서

    @Comment("사용유무")
    @Column(columnDefinition = "char",length = 1)
    private String useYn = "";// 사용여부 사용 : Y , 사용안함 : N

    @Comment("등록일자")
    @CreatedDate
    private LocalDateTime createDate; //등록일자

    @Comment("수정일자")
    @LastModifiedDate
    private LocalDateTime modifyDate; //수정일자

    @Version
    private Long version;


    @OneToMany(mappedBy = "product",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE},fetch = FetchType.LAZY)
    List<ProductItem> itemList = new ArrayList<>();

    @Builder
    public Product(ProductDto dto) {
        this.pNum = dto.getPNum();
        this.category = new Category();
        category.addCategCd(dto.getCategCd());
        this.pTitle = dto.getPTitle();
        this.pContent = dto.getPContent();
        this.ord  = dto.getOrd();
        this.useYn = dto.getUseYn();
        this.createDate = dto.getCreateDate();
        this.modifyDate = dto.getModifyDate();
    }


    public void addProductItem(ProductItem item){
        this.itemList.add(item);
    }


    public void addPNum(String pNum){
        this.pNum = pNum;
    }

    @Override
    public String getId() {
        return this.pNum;
    }

    @Override
    public boolean isNew() {
        return (this.createDate == null && (this.pNum == null || this.pNum.isEmpty()));
    }
}
