package com.flab.blackfriday.mongo.logging.system.service;

import com.flab.blackfriday.mongo.logging.system.dto.CmsSystemMongoLogDto;
import com.flab.blackfriday.mongo.logging.system.mgrepository.CmsSystemMongoRepository;
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
@Transactional(readOnly = true, value = "mongoTx")
public class CmsSystemMongoLogService {

    private final CmsSystemMongoRepository cmsSystemLogRepository;


    /**
     * System Log 등록
     * @param dto
     * @throws Exception
     */
    @Transactional(value = "mongoTx")
    public void createCmsSystemLog(CmsSystemMongoLogDto dto) throws Exception {
        System.out.println("---- ok ----");
        cmsSystemLogRepository.save(dto.toEntity());
    }
    
}
