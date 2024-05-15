package com.flab.blackfriday.product.coupon.service;

import com.flab.blackfriday.product.coupon.domain.ProductCoupon;
import com.flab.blackfriday.product.coupon.dto.ProductCouponDefaultDto;
import com.flab.blackfriday.product.coupon.dto.ProductCouponDto;
import com.flab.blackfriday.product.coupon.dto.ProductCouponSummaryResponse;
import com.flab.blackfriday.product.coupon.repository.ProductCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

}
