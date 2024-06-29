package com.flab.blackfriday.logging.system.repository;

import com.flab.blackfriday.logging.system.domain.CmsSystemLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.flab.blackfriday.logging.system.repository
 * fileName       : CmsSystemLogRepository
 * author         : rhkdg
 * date           : 2024-06-21
 * description    : 시스템 로그 repository
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-21        rhkdg       최초 생성
 */
public interface CmsSystemLogRepository extends JpaRepository<CmsSystemLog,Long> {
}
