package com.flab.blackfriday.auth.admin.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.flab.blackfriday.auth.admin.dto
 * fileName       : AdminResponse
 * author         : GAMJA
 * date           : 2024/05/03
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/03        GAMJA       최초 생성
 */
@Getter
@Setter
public class AdminResponse {

    private String id = "";

    private String name = "";

    public static AdminResponse of(AdminDto dto) {
        AdminResponse response = new AdminResponse();
        response.setId(dto.getId());
        response.setName(dto.getName());
        return response;
    }

}
