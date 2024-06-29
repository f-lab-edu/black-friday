package com.flab.blackfriday.modules.product.service;

import com.flab.blackfriday.modules.product.dto.ProductBlackFridayDefaultDto;
import com.flab.blackfriday.modules.product.dto.ProductBlackFridayDto;
import com.flab.blackfriday.modules.product.repository.ProductBlackFridayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.flab.blackfriday.product.service
 * fileName       : ProductBlackFridayService
 * author         : rhkdg
 * date           : 2024-05-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-01        rhkdg       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true,value = "mysqlTx")
public class ProductBlackFridayService {

    private final ProductBlackFridayRepository productBlackFridayRepository;

    /**
     * 블랙 프라이데이 목록 조횡 (페이징 o)
     * @param searchDto
     * @return
     * @throws Exception
     */
    public Page<ProductBlackFridayDto> selectProductBlackFridayPageList(ProductBlackFridayDefaultDto searchDto) throws Exception {
        return productBlackFridayRepository.selectProductBlackFridayPageList(searchDto);
    }

    /**
     * 블랙 프라이데이 상세 조회
     * @param dto
     * @return
     * @throws Exception
     */
    public ProductBlackFridayDto selectProductBlackFriday(ProductBlackFridayDto dto) throws Exception {
        return productBlackFridayRepository.selectProductBlackFriday(dto);
    }

    /**
     * 블랙 프라이데이 등록, 삭제
     * @param dto
     * @throws Exception
     */
    @Transactional(value = "mysqlTx")
    @CacheEvict(value = "product_most_blackfriday")
    public void saveProductBlackFriday(ProductBlackFridayDto dto) throws Exception {
        productBlackFridayRepository.save(dto.toEntity());
    }

    /**
     * 블랙 프라이데이 삭제
     * @param dto
     * @throws Exception
     */
    @Transactional(value = "mysqlTx")
    @CacheEvict(value = "product_most_blackfriday")
    public void deleteProductBlackFriday(ProductBlackFridayDto dto) throws Exception {
        productBlackFridayRepository.deleteById(dto.getIdx());
    }

}
