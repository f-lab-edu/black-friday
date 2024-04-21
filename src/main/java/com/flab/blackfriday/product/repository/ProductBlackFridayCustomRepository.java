package com.flab.blackfriday.product.repository;

import com.flab.blackfriday.product.domain.ProductBlackFriday;
import com.flab.blackfriday.product.dto.ProductBlackFridayDefaultDto;
import com.flab.blackfriday.product.dto.ProductBlackFridayDto;
import org.springframework.data.domain.Page;

/**
 * packageName    : com.flab.blackfriday.product.repository
 * fileName       : ProductBlackFridayCustomRepository
 * author         : GAMJA
 * date           : 2024/04/21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/21        GAMJA       최초 생성
 */
public interface ProductBlackFridayCustomRepository {

    /**
     * 블랙 프라이데이 환경 정보 목록 조회
     * @param searchDto
     * @return
     * @throws Exception
     */
    Page<ProductBlackFridayDto> selectProductBlackFridayPageList(ProductBlackFridayDefaultDto searchDto) throws Exception;

    /**
     * 블랙 프라이데이 환경 정보 상세 조회
     * @param dto
     * @return
     * @throws Exception
     */
    ProductBlackFridayDto selectProductBlackFriday(ProductBlackFridayDto dto) throws Exception;

}
