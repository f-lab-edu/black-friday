package com.flab.blackfriday.product.coupon.service;

import com.flab.blackfriday.product.coupon.domain.ProductCoupon;
import com.flab.blackfriday.product.coupon.domain.ProductCouponEpin;
import com.flab.blackfriday.product.coupon.dto.*;
import com.flab.blackfriday.product.coupon.repository.ProductCouponEpinRepository;
import com.flab.blackfriday.product.coupon.repository.ProductCouponRepository;
import com.flab.blackfriday.product.coupon.util.CouponRandomUtil;
import com.flab.blackfriday.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(readOnly = true)
public class ProductCouponService {

    private final ProductCouponRepository productCouponRepository;

    private final ProductCouponEpinRepository productCouponEpinRepository;

    private final Lock lock = new ReentrantLock();

    /**
     * 쿠폰 페이지 목록 (페이징 o )
     * @param searchDto
     * @return
     * @throws Exception
     */
    public Page<ProductCouponSummaryResponse> selectProductCouponPageList(ProductCouponDefaultDto searchDto) throws Exception {
        return productCouponRepository.selectProductCouponPageList(searchDto);
    }

    /**
     * 쿠폰 페이지 목록
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<ProductCouponSummaryResponse> selectProductCouponList(ProductCouponDefaultDto searchDto) throws Exception {
        return productCouponRepository.selectProductCouponList(searchDto);
    }

    /**
     * 쿠폰 상세 정보
     * @param dto
     * @return
     * @throws Exception
     */
    public ProductCouponDto selectProductCoupon(ProductCouponDto dto) throws Exception {
        return productCouponRepository.selectProductCoupon(dto);
    }

    /**
     * 쿠폰 설정 등록
     * @param dto
     * @throws Exception
     */
    @Transactional
    public void insertProductCoupon(ProductCouponDto dto) throws Exception {
        productCouponRepository.save(dto.toCreateEntity());
    }

    /**
     * 쿠폰 설정 수정
     * @param dto
     * @throws Exception
     */
    @Transactional
    public void updateProductCoupon(ProductCouponDto dto) throws Exception {
        productCouponRepository.findById(dto.getIdx()).ifPresent(productCoupon -> productCoupon.addProductUpdate(dto));
    }

    /**
     * 쿠폰 설정 삭제
     * @param dto
     * @throws Exception
     */
    @Transactional
    public void deleteProductCoupon(ProductCouponDto dto) throws Exception {
        productCouponRepository.deleteById(dto.getIdx());
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
        ProductCouponEpin productCouponEpin = productCouponEpinRepository.findById(epinDto.getId()).orElse(null);
        if(productCouponEpin == null) {
            return null;
        }
        return new ProductCouponEpinDto(productCouponEpin);
    }

    /**
     * 쿠폰 생성
     * @param epinDto
     * @throws Exception
     */
    @Transactional
    public void insertProductCouponEpin(ProductCouponEpinDto epinDto) throws Exception {
        lock.lock();
        try {
            String couponNum = "";

            //쿠폰생성
            while (true) {
                couponNum = CouponRandomUtil.randomMix(10);
                ProductCouponEpin existCheck = productCouponEpinRepository.findById(couponNum).orElse(null);
                if (existCheck == null)
                    break;
            }

            epinDto.setCouponNum(couponNum);
            productCouponEpinRepository.save(epinDto.toEntity());
        }finally {
            lock.unlock();
        }
    }

    /**
     * 쿠폰 수정
     * @param epinDto
     * @throws Exception
     */
    @Transactional
    public void updateProductCouponEpin(ProductCouponEpinDto epinDto) throws Exception {
        productCouponEpinRepository.findById(epinDto.getCouponNum()).ifPresent(productCouponEpin -> productCouponEpin.addUpdateEpin(epinDto));
    }

}
