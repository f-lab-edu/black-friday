package com.flab.blackfriday.modules.product.service;

import com.flab.blackfriday.modules.product.domain.Product;
import com.flab.blackfriday.modules.product.domain.ProductItem;
import com.flab.blackfriday.modules.product.dto.*;
import com.flab.blackfriday.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
@Transactional(readOnly = true,value = "mysqlTx")
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
     * 블랙 프라이데이 포함된 목록 조회(페이징 x)
     * @param searchDto
     * @return
     * @throws Exception
     */
    @Cacheable(value="product_popular")
    public List<ProductSummaryResponse> selectProductPopulListWithBLackFriday(ProductDefaultDto searchDto) throws Exception {
        searchDto.setPopulYn("Y");
        return productRepository.selectProductListWithBLackFriday(searchDto);
    }

    /**
     * 할인가가 제일 높은 에 10명만 가져오기
     * @param searchDto
     * @return
     * @throws Exception
     */
    @Cacheable(value="product_most_blackfriday")
    public List<ProductSummaryResponse> selectProductListWithMostBlackFriday(ProductDefaultDto searchDto) throws Exception {
        return productRepository.selectProductListWithMostBlackFriday(searchDto);
    }

    /**
     * 블랙 프라이데이 임시 목록 조회
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<ProductTempResponse> selectProductPageWithItemTempList(ProductDefaultDto searchDto) throws Exception {
        return productRepository.selectProductPageWithItemTempList(searchDto);
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
    @Transactional(value = "mysqlTx")
    @CacheEvict(value = {"product_popular","product_most_blackfriday"})
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
    @Transactional(value = "mysqlTx")
    public boolean updateProductItemCnt(ProductItemDto itemDto) throws Exception{
        return productRepository.updateProductItemPcnt(itemDto);
    }

    /**
     * 삭제
     * @param dto
     * @throws Exception
     */
    @Transactional(value = "mysqlTx")
    @CacheEvict(value = {"product_popular","product_most_blackfriday"})
    public void deleteProduct(ProductDto dto) throws Exception {
        productRepository.delete(dto.toEntity());
    }

}
