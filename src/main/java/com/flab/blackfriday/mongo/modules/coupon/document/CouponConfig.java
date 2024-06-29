package com.flab.blackfriday.mongo.modules.coupon.document;

import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.mongo.modules.coupon.document
 * fileName       : CouponConfig
 * author         : rhkdg
 * date           : 2024-06-27
 * description    : 할인 쿠폰 설정 document
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-27        rhkdg       최초 생성
 */
@Getter
@Setter
@Document(collection = "coupon_config")
@NoArgsConstructor
@AllArgsConstructor
public class CouponConfig implements Persistable<String> {

    @Id
    private String id;

    @Field(name = "title")
    private String title;

    @Field(name = "remark")
    private String remark;

    @Field(name = "startDate")
    private String startDate;

    @Field(name = "endDate")
    private String endDate;

    @Field(name = "categCdGroup")
    private String categCdGroup;

    @Field(name = "productGroup")
    private String productGroup;

    @Field(name = "couponCnt")
    private int couponCnt;

    @Field(name = "sale")
    private int sale;

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
