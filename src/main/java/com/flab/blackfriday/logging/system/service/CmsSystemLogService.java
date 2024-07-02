package com.flab.blackfriday.logging.system.service;

import com.flab.blackfriday.logging.system.dto.CmsSystemLogDto;
import com.flab.blackfriday.logging.system.repository.CmsSystemLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.flab.blackfriday.logging.system.service
 * fileName       : CmsSystemLogService
 * author         : rhkdg
 * date           : 2024-06-21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-21        rhkdg       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true,value = "mysqlTx")
public class CmsSystemLogService {

    private final CmsSystemLogRepository cmsSystemLogRepository;


    /**
     * System Log 등록
     * @param dto
     * @throws Exception
     */
    @Transactional(value = "mysqlTx")
    public void createCmsSystemLog(CmsSystemLogDto dto) throws Exception {
        System.out.println("---- ok ----");
        cmsSystemLogRepository.save(dto.toEntity());
    }
    
}
