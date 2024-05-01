package com.flab.blackfriday.product.controller;

import com.flab.blackfriday.common.controller.BaseController;
import com.flab.blackfriday.product.domain.ProductBlackFriday;
import com.flab.blackfriday.product.dto.ProductBlackFridayDefaultDto;
import com.flab.blackfriday.product.dto.ProductBlackFridayDto;
import com.flab.blackfriday.product.service.ProductBlackFridayService;
import com.flab.blackfriday.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * packageName    : com.flab.blackfriday.product.controller
 * fileName       : ProductBlackFridayController
 * author         : GAMJA
 * date           : 2024/04/21
 * description    : 블렉 프라이데이 설정 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/21        GAMJA       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class ProductBlackFridayController extends BaseController {

    private final ProductService productService;

    private final ProductBlackFridayService productBlackFridayService;

    /**
     * 목록 조회
     * @param searchDto
     * @return
     * @throws Exception
     */
    @GetMapping(MGN_URL+"/product/blackfriday/list")
    public Page<ProductBlackFridayDto> selectProductBlackFridayList(ProductBlackFridayDefaultDto searchDto) throws Exception {
        return productBlackFridayService.selectProductBlackFridayPageList(searchDto);
    }

    /**
     * 상세 조회
     * @param idx
     * @return
     * @throws Exception
     */
    @GetMapping(MGN_URL+"/product/blackfriday/view/{idx}")
    public ProductBlackFridayDto selectProductBlackFridayView(@PathVariable("idx") long idx) throws Exception {

        ProductBlackFridayDto dto = new ProductBlackFridayDto();
        dto.setIdx(idx);
        dto = productBlackFridayService.selectProductBlackFriday(dto);
        return dto;
    }

}
