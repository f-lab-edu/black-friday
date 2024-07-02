package com.flab.blackfriday.mongo.logging.system.mgrepository;

import com.flab.blackfriday.mongo.logging.system.document.CmsSystemMongoLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName    : com.flab.blackfriday.logging.system.repository
 * fileName       : CmsSystemMongoRepository
 * author         : rhkdg
 * date           : 2024-06-22
 * description    : 몽고 DB 시스템 로그 남기기
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-22        rhkdg       최초 생성
 */
@Repository
public interface CmsSystemMongoRepository extends MongoRepository<CmsSystemMongoLog,String> {
}
