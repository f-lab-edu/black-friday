package com.flab.blackfriday.mongo.modules.coupon.mgrepository;

import com.flab.blackfriday.mongo.modules.coupon.document.CouponEpin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName    : com.flab.blackfriday.mongo.coupon.repository
 * fileName       : CouponEpinRepository
 * author         : rhkdg
 * date           : 2024-06-23
 * description    : 쿠폰 발급 repository
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-23        rhkdg       최초 생성
 */
@Repository
public interface CouponEpinRepository extends MongoRepository<CouponEpin,String> {

    /**
     * configId 기준 개수
     * @param configId
     * @return
     */
    int countCouponEpinByConfigId(String configId);

    CouponEpin findFirstByConfigIdAndMemberNullOrderByCreateDateAsc(String config);

}
