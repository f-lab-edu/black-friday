package com.flab.blackfriday.mongo.modules.member;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : com.flab.blackfriday.mongo.modules.member
 * fileName       : Member
 * author         : rhkdg
 * date           : 2024-06-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-27        rhkdg       최초 생성
 */
@Data
public class Member {

    @Field(name="memberId")
    private String memberId;

    @Field(name="nickname")
    private String nickname;
}
