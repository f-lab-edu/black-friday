package com.flab.blackfriday.modules.product.coupon.controller;

import com.flab.blackfriday.common.controller.BaseController;
import com.flab.blackfriday.common.response.CommonResponse;
import com.flab.blackfriday.modules.product.coupon.service.ProductCouponService;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponDefaultDto;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponDto;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponSummaryResponse;
import com.flab.blackfriday.modules.product.coupon.dto.action.ProductCouponConfigDeleteRequest;
import com.flab.blackfriday.modules.product.coupon.dto.action.ProductCouponConfigRequest;
import com.flab.blackfriday.modules.product.coupon.dto.action.ProductCouponConfigUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : com.flab.blackfriday.product.coupon.controller
 * fileName       : ProductCouponController
 * author         : rhkdg
 * date           : 2024-06-02
 * description    : 쿠폰 환경 설정 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-02        rhkdg       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class ProductCouponConfigController extends BaseController {

    private final ProductCouponService productCouponService;


    /**
     * 쿠폰 환경 설정 정보 목록
     * @param searchDto
     * @return
     * @throws Exception
     */
    @GetMapping(value=MGN_URL+"/product/coupon/config/list")
    public Page<ProductCouponSummaryResponse> selectProductCouponPageList(ProductCouponDefaultDto searchDto) throws Exception {
        return productCouponService.selectProductCouponPageList(searchDto);
    }

    /**
     * 쿠폰 환경 설정 상세정보
     * @param productCouponDto
     * @return
     * @throws Exception
     */
    @GetMapping(value=MGN_URL+"/product/coupon/config/view")
    public ProductCouponDto selectProductCouponConfigView(ProductCouponDto productCouponDto) throws Exception {
        return productCouponService.selectProductCoupon(productCouponDto);
    }

    /**
     * 쿠폰 환경설정 신규등록
     * @param productCouponConfigRequest
     * @return
     * @throws Exception
     */
    @PostMapping(value=MGN_URL+"/product/coupon/config/ins")
    public ResponseEntity<?> insertProductCouponConfig(@RequestBody final ProductCouponConfigRequest productCouponConfigRequest) throws Exception {
        try{
            ProductCouponDto productCouponDto = ProductCouponDto.createOf(productCouponConfigRequest);
            productCouponService.insertProductCoupon(productCouponDto);
        }catch (Exception e) {
            logger.error("## insert product config error : {}",e.getMessage());
            return new ResponseEntity<>(new CommonResponse("쿠폰 환경설정 등록시 오류가 발생했습니다.",null), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok(new CommonResponse("등록 되었습니다.",null));
    }

    /**
     * 쿠폰 환경설정 수정
     * @param productCouponConfigUpdateRequest
     * @return
     * @throws Exception
     */
    @PutMapping(value=MGN_URL+"/product/coupon/config/upd")
    public ResponseEntity<?> updateProductCouponConfig(@RequestBody final ProductCouponConfigUpdateRequest productCouponConfigUpdateRequest) throws Exception {
        try{
            ProductCouponDto productCouponDto  = ProductCouponDto.updateOf(productCouponConfigUpdateRequest);
            productCouponService.updateProductCoupon(productCouponDto);
        }catch (Exception e) {
            logger.error("## update product config error : {}",e.getMessage());
            return new ResponseEntity<>(new CommonResponse("쿠폰 환경설정 수정시 오류가 발생했습니다.",null),HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok(new CommonResponse("수정 되었습니다.",null));
    }

    /**
     * 쿠폰 환경 설정 수정
     * @param productCouponConfigDeleteRequest
     * @return
     * @throws Exception
     */
    @DeleteMapping(MGN_URL+"/product/coupon/config/del")
    public ResponseEntity<?> deleteProductCouponConfig(@RequestBody final ProductCouponConfigDeleteRequest productCouponConfigDeleteRequest) throws Exception {
        try{
            ProductCouponDto productCouponDto = ProductCouponDto.deleteOf(productCouponConfigDeleteRequest);
            productCouponService.deleteProductCoupon(productCouponDto);
        }catch (Exception e){
            logger.error("### delete product config error : {}",e.getMessage());
            return new ResponseEntity<>(new CommonResponse("쿠폰 환경설정 삭제시 오류가 발생했습니다.",null),HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return ResponseEntity.ok(new CommonResponse("삭제 되었습니다.",null));
    }
}
