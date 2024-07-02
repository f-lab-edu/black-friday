package com.flab.blackfriday.modules.product.controller;

import com.flab.blackfriday.common.controller.BaseModuleController;
import com.flab.blackfriday.modules.product.dto.ProductSummaryResponse;
import com.flab.blackfriday.modules.product.service.ProductService;
import com.flab.blackfriday.modules.product.dto.ProductDefaultDto;
import com.flab.blackfriday.modules.product.dto.ProductDto;
import com.flab.blackfriday.modules.product.dto.ProductTempResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public Page<ProductSummaryResponse> selectProductList(ProductDefaultDto searchDto) throws Exception {
        return productService.selectProductPageList(searchDto);
    }


    /**
     * 블랙 프라이데이 적용된 상품 목록
     * @param searchDto
     * @return
     * @throws Exception
     */
    @GetMapping(API_URL+"/product/list/blackfriday")
    public Page<ProductSummaryResponse> selectProductBlackFridayList(ProductDefaultDto searchDto) throws Exception {
        //블랙프라이데이 할인 적용된 정보만 조회
        searchDto.setBlackFridayUseYn("Y");
        return productService.selectProductPageListWithBlackFriday(searchDto);
    }

    /**
     * 할인가 제일 많이 들어가는 목록 조회 (캐싱 적용)
     * @param searchDto
     * @return
     * @throws Exception
     */
    @GetMapping(API_URL+"/product/list/most/blackfriday")
    public List<ProductSummaryResponse> selectProductListWithMostBlackFriday(ProductDefaultDto searchDto) throws Exception {
        searchDto.setPage(1);
        searchDto.setSize(10);
        return productService.selectProductListWithMostBlackFriday(searchDto);
    }

    /**
     * 인기 상품 목록 조회( 캐싱 적용)
     * @param searchDto
     * @return
     * @throws Exception
     */
    @GetMapping(API_URL+"/product/popul/list")
    public List<ProductSummaryResponse> selectProductPopulListWithBLackFriday(ProductDefaultDto searchDto) throws Exception {
        searchDto.setUseYn("Y");
        return productService.selectProductPopulListWithBLackFriday(searchDto);
    }

    /**
     * 테스트
     * @param searchDto
     * @return
     * @throws Exception
     */
    @GetMapping(API_URL+"/product/list/v2/blackfriday")
    public List<ProductTempResponse> selectProductBlackV2FridayList(ProductDefaultDto searchDto) throws Exception {
        return productService.selectProductPageWithItemTempList(searchDto);
    }

    /**
     * 상품 상세 조회
     * @param pNum
     * @return
     * @throws Exception
     */
    @GetMapping(API_URL+"/product/view/{pNum}")
    public ProductSummaryResponse selectProductView(@PathVariable("pNum") String pNum) throws Exception {

        ProductDto productDto = new ProductDto();
        productDto.setPNum(pNum);
        productDto = productService.selectProduct(productDto);

        return ProductSummaryResponse.fromDto(productDto);
    }



}
