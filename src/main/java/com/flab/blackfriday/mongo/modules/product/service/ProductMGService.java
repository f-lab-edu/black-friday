package com.flab.blackfriday.mongo.modules.product.service;

import com.flab.blackfriday.modules.product.dto.ProductDefaultDto;
import com.flab.blackfriday.modules.product.dto.ProductDto;
import com.flab.blackfriday.modules.product.dto.ProductSummaryResponse;
import com.flab.blackfriday.modules.product.repository.ProductRepository;
import com.flab.blackfriday.modules.product.service.ProductService;
import com.flab.blackfriday.mongo.modules.product.document.Product;
import com.flab.blackfriday.mongo.modules.product.mgrepository.ProductMGRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * packageName    : com.flab.blackfriday.mongo.modules.product.service
 * fileName       : ProductService
 * author         : rhkdg
 * date           : 2024-06-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-27        rhkdg       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, value = "mongoTx")
public class ProductMGService {

    private final ProductMGRepository productMGRepository;

    private final ProductRepository productRepository;

    /**
     * 등록
     * @param product
     * @throws Exception
     */
    @Transactional(value = "mongoTx")
    public void insertProduct(Product product) throws Exception {

    }

    @Transactional(value = "mongoTx")
    public void insertProductBatch() throws  Exception {

        List<com.flab.blackfriday.modules.product.domain.Product> list = productRepository.findAll();

        for( com.flab.blackfriday.modules.product.domain.Product response : list){
            Product product = new Product();
            product.setPNum(response.getPNum());
            product.setTitle(response.getPTitle());
            product.setCategCd(response.getCategory().getCategCd());
            product.setContent(response.getPContent());
            product.setOrd(response.getOrd());
            product.setPopulYn(response.getPopulYn());
            product.setUseYn(response.getUseYn());
            productMGRepository.insert(product);
        }
    }

}
