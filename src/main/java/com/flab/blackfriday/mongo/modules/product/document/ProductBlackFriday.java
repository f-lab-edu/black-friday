package com.flab.blackfriday.mongo.modules.product.document;

import com.flab.blackfriday.modules.product.domain.Product;
import com.flab.blackfriday.modules.product.dto.ProductBlackFridayDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.FetchProfile;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.product.domain
 * fileName       : ProductBlackFriday
 * author         : rhkdg
 * date           : 2024-04-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-18        rhkdg       최초 생성
 */
@Document(collection="product_black_friday")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBlackFriday implements Persistable<String> {

    @Id
    private String id;

    @Field(name = "pNum")
    private String pNum;

    @Field(name = "sale")
    private int sale;

    @Field(name = "useYn")
    private String useYn;

    @Field(name = "createDate")
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
