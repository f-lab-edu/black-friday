package com.flab.blackfriday.mongo.modules.product.document;

import com.flab.blackfriday.modules.product.domain.Product;
import com.flab.blackfriday.modules.product.dto.ProductItemDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.product.domain
 * fileName       : ProductItem
 * author         : rhkdg
 * date           : 2024-04-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-18        rhkdg       최초 생성
 */
@Document(collection="product_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductItem implements Persistable<String> {

    @Id
    private String id;

    @Field(name = "pNum")
    private String pNum;

    @Field(name = "pItmName")
    private String pItmName;

    @Field(name = "pItmPrice")
    private int pItmPrice;

    @Field(name = "pItmCnt")
    private int pItmCnt;

    @Field(name = "pItmRemark")
    private String pItmRemark;

    @Field(name="createDate")
    @CreatedDate
    private LocalDateTime createDate;

    @Field(name = "modifyDate")
    @LastModifiedDate
    private LocalDateTime modifyDate;


    @Override
    public boolean isNew() {
        return this.createDate == null;
    }
}
