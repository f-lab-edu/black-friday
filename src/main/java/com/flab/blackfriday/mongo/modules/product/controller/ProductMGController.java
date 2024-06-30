package com.flab.blackfriday.mongo.modules.product.controller;

import com.flab.blackfriday.common.controller.BaseController;
import com.flab.blackfriday.mongo.modules.product.mgrepository.ProductMGRepository;
import com.flab.blackfriday.mongo.modules.product.service.ProductMGService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.flab.blackfriday.mongo.modules.product.controller
 * fileName       : ProductMGController
 * author         : rhkdg
 * date           : 2024-06-29
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-29        rhkdg       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class ProductMGController extends BaseController {

    private final ProductMGService productMGService;

    /**
     * 몽고 디비 업데이트
     * @return
     * @throws Exception
     */
    @PostMapping(value = MGN_URL+"/product/rdb/move/mogno")
    public ResponseEntity<?> insertProductMGBatch() throws Exception {
       productMGService.insertProductBatch();
        return ResponseEntity.ok("완료");
    }
}
