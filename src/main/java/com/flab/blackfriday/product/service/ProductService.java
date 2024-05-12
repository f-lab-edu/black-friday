package com.flab.blackfriday.product.service;

import com.flab.blackfriday.product.domain.Product;
import com.flab.blackfriday.product.domain.ProductItem;
import com.flab.blackfriday.product.dto.*;
import com.flab.blackfriday.product.repository.ProductBlackFridayRepository;
import com.flab.blackfriday.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : com.flab.blackfriday.product.service
 * fileName       : ProductService
 * author         : rhkdg
 * date           : 2024-04-19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-19        rhkdg       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 목록 조회(페이징 o)
     * @param searchDto
     * @return
     * @throws Exception
     */
    public Page<ProductSummaryResponse> selectProductPageList(ProductDefaultDto searchDto) throws Exception {
        return productRepository.selectProductPageList(searchDto);
    }

    /**
     * 블랙 프라이데이 포함된 목록 조회(페이징 o)
     * @param searchDto
     * @return
     * @throws Exception
     */
    public Page<ProductSummaryResponse> selectProductPageListWithBlackFriday(ProductDefaultDto searchDto) throws Exception {
        return productRepository.selectProductPageListWithBlackFriday(searchDto);
    }

    /**
     * 목록 조회(페이징 x)
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<ProductSummaryResponse> selectProductList(ProductDefaultDto searchDto) throws Exception {
        return productRepository.selectProductList(searchDto);
    }

    /**
     * 상세 조회
     * @param dto
     * @return
     * @throws Exception
     */
    public ProductDto selectProduct(ProductDto dto) throws Exception{
        dto = productRepository.selectProduct(dto);
        if(dto != null){
            ProductDefaultDto searchDto = new ProductDefaultDto();
            searchDto.setPNum(dto.getPNum());
            dto.setItemList(productRepository.selectProductItemList(searchDto));
        }
        return dto;
    }

    /**
     * 상품과 옵션을 등록/수정
     * @param dto
     * @throws Exception
     */
    @Transactional
    public void saveProduct(ProductDto dto) throws Exception {
        Product product = dto.toEntity();
        //옵션 정보가 있을 경우 담는다.
        for(ProductItemDto itemDto : dto.getItemList()) {
            ProductItem item = itemDto.toEntity();
            product.addProductItem(item);
        }
        productRepository.save(product);
    }

    /**
     * 옵션 카운트 업데이트
     * @param itemDto
     * @throws Exception
     */
    @Transactional
    public boolean updateProductItemCnt(ProductItemDto itemDto) throws Exception{
        return productRepository.updateProductItemPcnt(itemDto);
    }

    /**
     * 삭제
     * @param dto
     * @throws Exception
     */
    @Transactional
    public void deleteProduct(ProductDto dto) throws Exception {
        productRepository.delete(dto.toEntity());
    }

}
