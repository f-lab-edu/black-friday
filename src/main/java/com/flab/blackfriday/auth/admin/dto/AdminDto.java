package com.flab.blackfriday.auth.admin.dto;

import com.flab.blackfriday.auth.admin.domain.Admin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : com.flab.blackfriday.auth.admin.dto
 * fileName       : AdminDto
 * author         : rhkdg
 * date           : 2024-04-18
 * description    : 관리자 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-18        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class AdminDto {

    private String id = "";

    private String name = "";

    private String password = "";

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    /**
     * dto -> entity
     * @return
     */
    public Admin toEntity() {
        return Admin.builder().dto(this).build();
    }

    public static AdminDto of(Admin admin){
        AdminDto dto = new AdminDto();
        dto.setId(admin.getId());
        dto.setPassword(admin.getPassword());
        dto.setCreateDate(admin.getCreateDate());
        dto.setModifyDate(admin.getModifyDate());
        return dto;
    }

}
