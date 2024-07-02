package com.flab.blackfriday.mongo.modules.product.document;

import com.flab.blackfriday.category.domain.Category;
import com.flab.blackfriday.modules.product.domain.ProductItem;
import com.flab.blackfriday.modules.product.dto.ProductDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product")
public class Product implements Persistable<String> {

    //상품 아이디
    @Id
    private String id;

    @Indexed
    @Field(name = "pNum")
    private String pNum;

    @Field(name = "categCd")
    private String categCd;

    // 상품 제목
    @Field(name = "title")
    private String title;

    // 상품 상세내용
    @Field(name = "content")
    private String content; 

    //정렬 순서
    @Field(name = "ord")
    private int ord =0; 

    // 사용여부 사용 : Y , 사용안함 : N
    @Field(name = "useYn")
    private String useYn; 

    @Field(name = "createDate")
    @CreatedDate
    private LocalDateTime createDate; //등록일자

    @Field(name = "modifyDate")
    @LastModifiedDate
    private LocalDateTime modifyDate; //수정일자

    @Field(name = "populYn")
    private String populYn;

    @Override
    public boolean isNew() {
        return this.createDate == null;
    }
}
