package com.flab.blackfriday.product.controller;

import com.flab.blackfriday.common.controller.BaseModuleController;
import com.flab.blackfriday.product.dto.ProductDefaultDto;
import com.flab.blackfriday.product.dto.ProductDto;
import com.flab.blackfriday.product.dto.ProductSummaryResponse;
import com.flab.blackfriday.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * packageName    : com.flab.blackfriday.product.controller
 * fileName       : ProductUserController
 * author         : GAMJA
 * date           : 2024/04/20
 * description    : 사용자 상품 관련 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/20        GAMJA       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class ProductUserController extends BaseModuleController {

    private final ProductService productService;


    /**
     * 상품 목록 조회
     * @param searchDto
     * @return
     * @throws Exception
     */
    @GetMapping(API_URL+"/product/list")
    public Map<String,Object> selectProductList(ProductDefaultDto searchDto) throws Exception {

        Page<ProductSummaryResponse> resultList = productService.selectProductPageList(searchDto);
        modelMap.put("productList",resultList);

        return modelMap;
    }


    /**
     * 블랙 프라이데이 적용된 상품 목록
     * @param searchDto
     * @return
     * @throws Exception
     */
    @GetMapping(API_URL+"/product/list/blackfriday")
    public Map<String,Object> selectProductBlackFridayList(ProductDefaultDto searchDto) throws Exception {

        //블랙프라이데이 할인 적용된 정보만 조회
        searchDto.setBlackFridayUseYn("Y");
        Page<ProductSummaryResponse> resultList = productService.selectProductBlackFridayPageList(searchDto);
        modelMap.put("productList",resultList);

        return modelMap;
    }

    /**
     * 상품 상세 조회
     * @param pNum
     * @return
     * @throws Exception
     */
    @GetMapping(API_URL+"/product/view/{pNum}")
    public Map<String,Object> selectProductView(@PathVariable("pNum") String pNum) throws Exception {

        ProductDto productDto = new ProductDto();
        productDto.setPNum(pNum);
        productDto = productService.selectProduct(productDto);
        modelMap.put("productDto",productDto);

        return modelMap;
    }



}
