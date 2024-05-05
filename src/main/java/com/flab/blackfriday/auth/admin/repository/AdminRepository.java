package com.flab.blackfriday.auth.admin.repository;

import com.flab.blackfriday.auth.admin.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.flab.blackfriday.auth.admin.repository
 * fileName       : AdminRepository
 * author         : rhkdg
 * date           : 2024-05-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-01        rhkdg       최초 생성
 */
public interface AdminRepository extends JpaRepository<Admin,String> {
}
