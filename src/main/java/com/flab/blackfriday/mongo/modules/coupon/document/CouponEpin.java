package com.flab.blackfriday.mongo.modules.coupon.document;

import com.flab.blackfriday.auth.member.dto.MemberDto;
import com.flab.blackfriday.mongo.modules.member.Member;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.mongo.coupon.domain
 * fileName       : CouponEpin
 * author         : rhkdg
 * date           : 2024-06-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-23        rhkdg       최초 생성
 */
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "coupon_epin")
public class CouponEpin implements Persistable<String> {

    @Id
    private String id;

    @Indexed
    @Field(name="couponNum")
    private String couponNum;

    @Field(name="configId")
    private String configId;

    @Field(name="member")
    private Member member;

    @Field(name="useType")
    private String useType;

    @Field(name="useStatus")
    private String useStatus;

    @Field(name="createDate")
    @CreatedDate
    private LocalDateTime createDate;

    @Field(name="modifyDate")
    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Override
    public boolean isNew() {
        return createDate == null;
    }
}
