package com.flab.blackfriday.product.controller;

import com.flab.blackfriday.common.controller.BaseController;
import com.flab.blackfriday.product.dto.ProductDefaultDto;
import com.flab.blackfriday.product.dto.ProductDto;
import com.flab.blackfriday.product.dto.ProductRequest;
import com.flab.blackfriday.product.dto.ProductSummaryResponse;
import com.flab.blackfriday.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * packageName    : com.flab.blackfriday.product.controller
 * fileName       : ProductController
 * author         : GAMJA
 * date           : 2024/04/21
 * description    : 관리자 상품 관리 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/21        GAMJA       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class ProductController extends BaseController {

    private final ProductService productService;

    /**
     * 상품 목록 페이징(o)
     * @param searchDto
     * @return
     * @throws Exception
     */
    @GetMapping(MGN_URL+"/product/list")
    public Page<ProductSummaryResponse> selectProductList(ProductDefaultDto searchDto) throws Exception {
        return productService.selectProductPageList(searchDto);
    }

    /**
     * 상품 상세 조회
     * @param pNum
     * @return
     * @throws Exception
     */
    @GetMapping(MGN_URL+"/product/view/{pNum}")
    public ProductDto selectProductView(@PathVariable("pNum") String pNum) throws Exception {

        ProductDto productDto = new ProductDto();
        productDto.setPNum(pNum);
        productDto = productService.selectProduct(productDto);

        return productDto;
    }

    /**
     * 상품 등록
     * @param productRequest
     * @return
     * @throws Exception
     */
    @PostMapping(MGN_URL+"/product/ins")
    public ResponseEntity<?> insertProduct(@RequestBody @Valid ProductRequest productRequest) throws Exception {

        try{
            productService.saveProduct(ProductDto.requestOf(productRequest));
        }catch (Exception e){
            logger.error("### insert product error : {}",e.getMessage());
            modelMap.put("msg","상품 정보 등록시 오류가 발생했습니다.");
            return new ResponseEntity<>(modelMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        modelMap.put("msg","등록 되었습니다.");
        return new ResponseEntity<>(modelMap,HttpStatus.OK);
    }

    /**
     * 상품 수정
     * @param productRequest
     * @return
     * @throws Exception
     */
    @PutMapping(MGN_URL+"/product/upd")
    public ResponseEntity<?> updateProduct(@RequestBody @Valid ProductRequest productRequest) throws Exception {
        try{
            productService.saveProduct(ProductDto.requestOf(productRequest));
        }catch (Exception e) {
            logger.error("### update product error : {}",e.getMessage());
            modelMap.put("msg","상품 정보 수정시 오류가 발생했습니다.");
            return new ResponseEntity<>(modelMap,HttpStatus.UNPROCESSABLE_ENTITY);
        }

        modelMap.put("msg","수정 되었습니다.");
        return new ResponseEntity<>(modelMap,HttpStatus.OK);
    }


    @DeleteMapping(MGN_URL+"/product/del")
    public ResponseEntity<?> deleteProduct(@RequestParam("pNum") String pNum) throws Exception {

        try{
            ProductDto dto = new ProductDto();
            dto.setPNum(pNum);
            productService.deleteProduct(dto);
        }catch (Exception e) {
            logger.error("### delete product error : {}",e.getMessage());
            modelMap.put("msg","상품 정보 삭제시 오류가 발생했습니다.");
            return new ResponseEntity<>(modelMap,HttpStatus.UNPROCESSABLE_ENTITY);
        }

        modelMap.put("msg","삭제 되었습니다.");
        return new ResponseEntity<>(modelMap,HttpStatus.OK);
    }

}
