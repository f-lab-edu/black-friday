package com.flab.blackfriday.mongo.modules.coupon.mgrepository;

import com.flab.blackfriday.mongo.modules.coupon.document.CouponConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName    : com.flab.blackfriday.mongo.modules.coupon.mgrepository
 * fileName       : CouponConfigRepositorry
 * author         : rhkdg
 * date           : 2024-06-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-27        rhkdg       최초 생성
 */
@Repository
public interface CouponConfigRepository extends MongoRepository<CouponConfig,String> {
}
