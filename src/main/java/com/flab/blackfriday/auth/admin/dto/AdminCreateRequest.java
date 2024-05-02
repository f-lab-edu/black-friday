package com.flab.blackfriday.auth.admin.dto;

import com.flab.blackfriday.auth.admin.domain.Admin;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.auth.admin.dto
 * fileName       : AdminCreateRequest
 * author         : GAMJA
 * date           : 2024/05/03
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/03        GAMJA       최초 생성
 */
@Setter
@Getter
public class AdminCreateRequest {

    /**아이디*/
    private String id = "";

    /**이름*/
    private String name = "";

    /**비밀번호*/
    private String password = "";

    public Admin toCreateEntity() {
        return Admin.createAdmin().request(this).build();
    }

}
