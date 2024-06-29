package com.flab.blackfriday.modules.product.coupon.service;

import com.flab.blackfriday.common.exception.BaseException;
import com.flab.blackfriday.common.exception.CommonNotUseException;
import com.flab.blackfriday.common.exception.NoExistAuthException;
import com.flab.blackfriday.modules.order.exception.OrderValidatorException;
import com.flab.blackfriday.modules.product.coupon.domain.ProductCouponConfig;
import com.flab.blackfriday.modules.product.coupon.domain.ProductCouponEpin;
import com.flab.blackfriday.modules.product.coupon.dto.*;
import com.flab.blackfriday.modules.product.coupon.repository.ProductCouponConfigRepository;
import com.flab.blackfriday.modules.product.coupon.repository.ProductCouponEpinRepository;
import com.flab.blackfriday.modules.product.coupon.util.CouponRandomUtil;
import com.flab.blackfriday.modules.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * packageName    : com.flab.blackfriday.product.coupon.service
 * fileName       : ProductCouponService
 * author         : GAMJA
 * date           : 2024/05/15
 * description    : 쿠폰 관련 service
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/15        GAMJA       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true,value = "mysqlTx")
public class ProductCouponService {

    private final ProductCouponConfigRepository productCouponConfigRepository;

    private final ProductCouponEpinRepository productCouponEpinRepository;

    private final Lock lock = new ReentrantLock();

    /**
     * 쿠폰 환경 페이지 목록 (페이징 o )
     * @param searchDto
     * @return
     * @throws Exception
     */
    public Page<ProductCouponSummaryResponse> selectProductCouponPageList(ProductCouponDefaultDto searchDto) throws Exception {
        return productCouponConfigRepository.selectProductCouponPageList(searchDto);
    }

    /**
     * 쿠폰 환경 페이지 목록
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<ProductCouponSummaryResponse> selectProductCouponList(ProductCouponDefaultDto searchDto) throws Exception {
        return productCouponConfigRepository.selectProductCouponList(searchDto);
    }

    /**
     * 쿠폰 환경설정 상세 정보
     * @param dto
     * @return
     * @throws Exception
     */
    public ProductCouponDto selectProductCoupon(ProductCouponDto dto) throws Exception {
        return productCouponConfigRepository.selectProductCoupon(dto);
    }

    /**
     * 쿠폰 설정 등록
     * @param dto
     * @throws Exception
     */
    @Transactional(value = "mysqlTx")
    public void insertProductCoupon(ProductCouponDto dto) throws Exception {
        productCouponConfigRepository.save(dto.toCreateEntity());
    }

    /**
     * 쿠폰 설정 수정
     * @param dto
     * @throws Exception
     */
    @Transactional(value = "mysqlTx")
    public void updateProductCoupon(ProductCouponDto dto) throws Exception {
        productCouponConfigRepository.findById(dto.getIdx()).ifPresent(productCoupon -> productCoupon.addProductUpdate(dto));
    }

    /**
     * 쿠폰 설정 삭제
     * @param dto
     * @throws Exception
     */
    @Transactional(value = "mysqlTx")
    public void deleteProductCoupon(ProductCouponDto dto) throws Exception {
        ProductCouponDefaultDto searchDto = new ProductCouponDefaultDto();
        searchDto.setProductCouponIdx(dto.getIdx());
        Page<ProductCouponEpinWithInfoResponse> list = productCouponEpinRepository.selectProductCouponEpinPageList(searchDto);
        if(list.getSize() > 0){
            throw new CommonNotUseException("이미 해당 내용으로 발급된 쿠폰이 존재합니다.");
        }
        productCouponConfigRepository.deleteById(dto.getIdx());
    }

    /**
     * 쿠폰 목록 조회(페이징)
     * @param searchDto
     * @return
     * @throws Exception
     */
    public Page<ProductCouponEpinWithInfoResponse> selectProductCouponEpinPageList(ProductCouponDefaultDto searchDto) throws Exception {
        return productCouponEpinRepository.selectProductCouponEpinPageList(searchDto);
    }

    /**
     * 쿠폰 목록 조회 (페이징 x)
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<ProductCouponEpinWithInfoResponse> selectProductCouponEpinList(ProductCouponDefaultDto searchDto) throws Exception {
        return productCouponEpinRepository.selectProductCouponEpinList(searchDto);
    }

    /**
     * 쿠폰 정보 조회
     * @param epinDto
     * @return
     * @throws Exception
     */
    public ProductCouponEpinDto selectProductCouponEpin(ProductCouponEpinDto epinDto) throws Exception {
        return  productCouponEpinRepository.selectProductCouponEpin(epinDto);
    }

    /**
     * 쿠폰 발급 정보 제공(미리 생성된 쿠폰 중 발급 되지 않는 번호를 제공)
     * @param idx
     * @return
     * @throws Exception
     */
    public String selectProductCouponEpinIssueToMember(long idx) throws Exception {
        return productCouponEpinRepository.selectProductEpinFirstCouponNum(idx);
    }

    /**
     * 쿠폰 생성
     * @param epinDto
     * @throws Exception
     */
    @Transactional(value = "mysqlTx")
    public void insertProductCouponEpin(ProductCouponEpinDto epinDto) throws Exception {
//        lock.lock();
        try {

            ProductCouponConfig productCoupon = productCouponConfigRepository.findById(epinDto.getIdx()).orElse(null);
            if(productCoupon == null ){
                throw new NoExistAuthException("잘못된 접근입니다.");
            }

            if(productCoupon.getCouponCnt() <= 0){
                throw new CommonNotUseException("해당 상품에 대한 쿠폰을 생성할 수 없습니다.");
            }

            //쿠폰 생성
            productCouponEpinRepository.save(epinDto.toEntity());

            //쿠폰 환경 정보 마이너스 처리
            productCoupon.minusCnt(1);

        }finally {
//            lock.unlock();
        }
    }

    /**
     * 쿠폰 미리 발급
     * @param dto
     * @throws Exception
     */
    @Transactional(value = "mysqlTx")
    public void insertProductCouponEpin2(ProductCouponDto dto) throws Exception {
        
        ProductCouponConfig productCoupon = productCouponConfigRepository.findById(dto.getIdx()).orElse(null);
        if(productCoupon == null){
            throw new NoExistAuthException("존재 하지 않는 쿠폰 이벤트 입니다.");
        }

        long totCnt = productCouponEpinRepository.selectPRoductEpinCntFromConfig(dto.getIdx());
        if(totCnt == productCoupon.getCouponCnt()) {
            throw new CommonNotUseException("더이상 발급이 불가능 합니다.");
        }

        //쿠폰 생성
        int cnt = 1;
        while(true) {
            ProductCouponEpinDto epinDto = new ProductCouponEpinDto();
            if(cnt > productCoupon.getCouponCnt()){
                break;
            }
            epinDto.setCouponNum(CouponRandomUtil.createUuid(10) + dto.getIdx());
            epinDto.setUseStatus(CouponUseStatus.NONE.name());
            epinDto.setUseType(CouponUseType.NOW.name());
            epinDto.setIdx(dto.getIdx());
            productCouponEpinRepository.save(epinDto.toEntity());
            cnt++;
        }

    }

    /**
     * 쿠폰 수정
     * @param epinDto
     * @throws Exception
     */
    @Transactional(value = "mysqlTx")
    public void updateProductCouponEpin(ProductCouponEpinDto epinDto) throws Exception {
        productCouponEpinRepository.findById(epinDto.getCouponNum()).ifPresent(productCouponEpin -> productCouponEpin.addUpdateEpin(epinDto));
    }

    /**
     * 쿠폰 발급 업데이트
     * @param epinDto
     * @return
     * @throws Exception
     */
    @Transactional(value = "mysqlTx")
    public int updateProductCouponEpinCompareAndSet(ProductCouponEpinDto epinDto) throws  Exception {
        return productCouponEpinRepository.updateProductCouponEpinCompareAndSet(epinDto);
    }


}
