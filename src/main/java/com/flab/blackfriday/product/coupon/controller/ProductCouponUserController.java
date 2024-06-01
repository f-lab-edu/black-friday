package com.flab.blackfriday.product.coupon.controller;

import com.flab.blackfriday.common.controller.BaseModuleController;
import com.flab.blackfriday.common.dto.ResultVO;
import com.flab.blackfriday.product.coupon.dto.CouponUseStatus;
import com.flab.blackfriday.product.coupon.dto.ProductCouponDefaultDto;
import com.flab.blackfriday.product.coupon.dto.ProductCouponEpinDto;
import com.flab.blackfriday.product.coupon.dto.ProductCouponEpinWithInfoResponse;
import com.flab.blackfriday.product.coupon.dto.action.ProductCouponEpinRequest;
import com.flab.blackfriday.product.coupon.dto.action.ProductCouponEpinUpdateRequest;
import com.flab.blackfriday.product.coupon.service.ProductCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : com.flab.blackfriday.product.coupon.controller
 * fileName       : ProductCouponUserController
 * author         : GAMJA
 * date           : 2024/05/26
 * description    : 쿠폰 생성 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/26        GAMJA       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class ProductCouponUserController extends BaseModuleController {

    private final ProductCouponService productCouponService;

    /**
     * 쿠폰 정보 조회
     * @param searchDto
     * @return
     * @throws Exception
     */
    @GetMapping(value=API_URL+"/product/coupon/epin/list")
    public Page<ProductCouponEpinWithInfoResponse> selectProductCouponEpinPageList(ProductCouponDefaultDto searchDto) throws Exception {
        return productCouponService.selectProductCouponEpinPageList(searchDto);
    }

    /**
     * 쿠폰 생성
     * @param productCouponEpinRequest
     * @return
     * @throws Exception
     */
    @PostMapping(value=API_URL+"/product/coupon/epin/{useType}/create")
    public ResponseEntity<?> insertProductCouponEpin(@RequestBody  ProductCouponEpinRequest productCouponEpinRequest,
                                                     @PathVariable String useType)  throws Exception {

        try{
            productCouponEpinRequest.setUseType(useType);
            productCouponEpinRequest.setUseStatus(CouponUseStatus.NONE.name());
            productCouponService.insertProductCouponEpin(ProductCouponEpinDto.createOf(productCouponEpinRequest));
        }catch(Exception e) {
            return new ResponseEntity<>("쿠폰생성시 오류가 발생하였습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok().body(new ResultVO("OK","쿠폰 생성되었습니다."));
    }

    /**
     * 쿠폰 수정 완료(사용여부)
     * @param productCouponEpinUpdateRequest
     * @return
     * @throws Exception
     */
    @PostMapping(value=API_URL+"/product/coupon/epin/{useStatus}/update")
    public ResponseEntity<?> updateProductCouponEpin(@RequestBody ProductCouponEpinUpdateRequest productCouponEpinUpdateRequest,
                                                     @PathVariable String useStatus)  throws Exception {

        try{
            ProductCouponEpinDto epinDto = ProductCouponEpinDto.updateOf(productCouponEpinUpdateRequest);
            epinDto.setUseStatus(useStatus);
            productCouponService.updateProductCouponEpin(epinDto);
        }catch(Exception e) {
            return new ResponseEntity<>("쿠폰수정시 오류가 발생하였습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok().body(new ResultVO("OK"));
    }
}
