package com.flab.blackfriday.mongo.modules.coupon.service;

import com.flab.blackfriday.common.exception.CommonNotUseException;
import com.flab.blackfriday.common.exception.NoExistAuthException;
import com.flab.blackfriday.modules.product.coupon.dto.CouponUseStatus;
import com.flab.blackfriday.modules.product.coupon.dto.CouponUseType;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponEpinDto;
import com.flab.blackfriday.modules.product.coupon.util.CouponRandomUtil;
import com.flab.blackfriday.mongo.modules.coupon.document.CouponConfig;
import com.flab.blackfriday.mongo.modules.coupon.document.CouponEpin;
import com.flab.blackfriday.mongo.modules.coupon.mgrepository.CouponConfigRepository;
import com.flab.blackfriday.mongo.modules.coupon.mgrepository.CouponEpinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.flab.blackfriday.mongo.coupon.service
 * fileName       : CouponEpinService
 * author         : rhkdg
 * date           : 2024-06-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-23        rhkdg       최초 생성
 */
@Service("couponMongoService")
@RequiredArgsConstructor
@Transactional(readOnly = true,value = "mongoTx")
public class CouponService {

    private final CouponConfigRepository couponConfigRepository;

    private final CouponEpinRepository couponEpinRepository;

    @Transactional(value = "mongoTx")
    public void insertCouponEpinCreate(CouponConfig config) throws Exception {
        CouponConfig couponConfig = couponConfigRepository.findById(config.getId()).orElse(null);
        if(couponConfig == null) {
            throw new NoExistAuthException("존재하지 않는 할인 이벤트 입니다.");
        }

        int totCnt = couponEpinRepository.countCouponEpinByConfigId(couponConfig.getId());
        if(couponConfig.getCouponCnt() == totCnt){
            throw new CommonNotUseException("더이상 발급이 불가능 합니다.");
        }

        //쿠폰 생성
        int cnt = 1;
        while(true) {
            if(cnt > couponConfig.getCouponCnt()){
                break;
            }
            CouponEpin couponEpin = new CouponEpin();
            couponEpin.setCouponNum(CouponRandomUtil.createUuid(14));
            couponEpin.setUseStatus(CouponUseStatus.NONE.name());
            couponEpin.setUseType(CouponUseType.NOW.name());
            couponEpin.setConfigId(config.getId());
            couponEpinRepository.save(couponEpin);
            cnt++;
        }
    }

    @Transactional(value = "mongoTx")
    public String updateCouponEpinToMember(String id) throws Exception {
        CouponEpin coupon = couponEpinRepository.findFirstByConfigIdAndMemberNullOrderByCreateDateAsc(id);
        if(coupon == null){
            throw new NoExistAuthException("더이상 발급이 불가능합니다.");
        }
        return coupon.getCouponNum();
    }
}
