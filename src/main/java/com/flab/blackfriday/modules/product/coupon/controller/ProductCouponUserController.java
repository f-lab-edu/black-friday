package com.flab.blackfriday.modules.product.coupon.controller;

import com.flab.blackfriday.auth.member.dto.MemberSession;
import com.flab.blackfriday.common.controller.BaseModuleController;
import com.flab.blackfriday.common.dto.ResultVO;
import com.flab.blackfriday.common.exception.NoExistAuthException;
import com.flab.blackfriday.modules.product.coupon.domain.ProductCouponEpin;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponDefaultDto;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponDto;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponEpinDto;
import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponEpinWithInfoResponse;
import com.flab.blackfriday.modules.product.coupon.dto.action.ProductCouponEpinRequest;
import com.flab.blackfriday.modules.product.coupon.dto.action.ProductCouponEpinUpdateRequest;
import com.flab.blackfriday.modules.product.coupon.kafka.CouponConsumerService;
import com.flab.blackfriday.modules.product.coupon.kafka.CouponProducerService;
import com.flab.blackfriday.modules.product.coupon.service.ProductCouponAppService;
import com.flab.blackfriday.modules.product.coupon.service.ProductCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

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

    private final ProductCouponAppService productCouponAppService;

    private final CouponProducerService couponProducerService;

    private final MemberSession memberSession;

    private final CouponConsumerService couponConsumerService;

    /**
     * 쿠폰 정보 조회
     * @param searchDto
     * @return
     * @throws Exception
     */
    @GetMapping(value=API_URL+"/product/coupon/epin/list")
    public Page<ProductCouponEpinWithInfoResponse> selectProductCouponEpinPageList(ProductCouponDefaultDto searchDto) throws Exception {
        if(!memberSession.isAuthenticated()){
            logger.error("### 인증되지 않은 접근. ### ");
            throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.",HttpStatus.UNAUTHORIZED.name());
        }
        return productCouponService.selectProductCouponEpinPageList(searchDto);
    }

    /**
     * 쿠폰 상세 정보
     * @param epinDto
     * @return
     * @throws Exception
     */
    @GetMapping(value=API_URL+"/product/coupon/epin/view")
    public ProductCouponEpinDto selectProductCouponEpin(ProductCouponEpinDto epinDto) throws Exception {
        if(!memberSession.isAuthenticated()){
            logger.error("### 인증되지 않은 접근. ### ");
            throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.",HttpStatus.UNAUTHORIZED.name());
        }
        epinDto.setId(memberSession.getMemberSession().getId());
        epinDto = productCouponService.selectProductCouponEpin(epinDto);
        ProductCouponDto productCouponDto = new ProductCouponDto();
        productCouponDto.setIdx(epinDto.getIdx());
        epinDto.setProductCouponDto(productCouponService.selectProductCoupon(productCouponDto));
        return epinDto;
    }

    /**
     * 쿠폰 생성
     * @param productCouponEpinRequest
     * @return
     * @throws Exception
     */
    @PostMapping(value=API_URL+"/product/coupon/epin/{useType}/create")
    public DeferredResult<?> createProductCouponEpin(@RequestBody ProductCouponEpinRequest productCouponEpinRequest,
                                                                  @PathVariable String useType)  throws Exception {

        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>(3000L);//timeout 3초

        try{
            if(!memberSession.isAuthenticated()){
                logger.error("### 인증되지 않은 접근. ### ");
                throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.",HttpStatus.UNAUTHORIZED.name());
            }
            productCouponEpinRequest.setUseType(useType);

            //비동기 컨슈머 결과 확인
            CompletableFuture<String> future = couponConsumerService.waitForString(productCouponEpinRequest.getId()+"_"+productCouponEpinRequest.getIdx());
            future.thenAccept(result -> {
                deferredResult.setResult(ResponseEntity.ok(result));
            })
            .exceptionally(e -> {
                deferredResult.setResult(new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY));
                deferredResult.setErrorResult(e);
                return null;
            });

            couponProducerService.sendMessage(productCouponEpinRequest);
        }catch(Exception e) {
              deferredResult.setResult(new ResponseEntity<>("쿠폰생성시 오류가 발생하였습니다.", HttpStatus.UNPROCESSABLE_ENTITY));
              deferredResult.setErrorResult(e);
              return deferredResult;
        }
        return deferredResult;
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


    /**
     * 쿠폰 발급 결과 (효과 없음..)
     * @return
     */
    @PostMapping(API_URL+"/coupon/create/result")
    public ResponseEntity<?> couponCreateResult(long idx) throws Exception {

        if(!memberSession.isAuthenticated()){
            return new ResponseEntity<>("회원 인증을 해주시기 바랍니다.", HttpStatus.UNAUTHORIZED);
        }

        Map<String,String> map = couponConsumerService.getCouponMap();
        if(map == null){
            return new ResponseEntity<>("쿠폰 함에서 확인해주시기 바랍니다.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        String id = map.get("id");
        long num = Long.parseLong(map.get("idx"));
        if(!id.equals(memberSession.getMemberSession().getId()) || !Objects.equals(idx,num)){
            return new ResponseEntity<>("쿠폰 정보 확인시 오류가 발생했습니다. 쿠폰 함에서 확인해주시기 바랍니다.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok(map.get("couponNum"));
    }


    /**
     * 쿠폰 생성
     * @param productCouponEpinRequest
     * @return
     * @throws Exception
     */
    @PostMapping(value=API_URL+"/product/coupon/epin/create")
    public ResponseEntity<?>  createProductCouponEpin2(@RequestBody ProductCouponEpinRequest productCouponEpinRequest)  throws Exception {

        if(!memberSession.isAuthenticated()){
            logger.error("### 인증되지 않은 접근. ### ");
            throw new NoExistAuthException("회원 인증을 진행해주시기 바랍니다.",HttpStatus.UNAUTHORIZED.name());
        }

        ProductCouponEpinDto epinDto = ProductCouponEpinDto.createOf(productCouponEpinRequest);
        epinDto.setId(memberSession.getMemberSession().getId());
        String couponNum = productCouponAppService.updateProductCouponEpinIssueToMember(epinDto);

        return ResponseEntity.ok(couponNum);
    }
}
