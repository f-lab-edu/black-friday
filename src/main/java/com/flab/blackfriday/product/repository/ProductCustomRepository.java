package com.flab.blackfriday.product.repository;

import com.flab.blackfriday.product.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * packageName    : com.flab.blackfriday.product.repository
 * fileName       : ProductCustomRepository
 * author         : rhkdg
 * date           : 2024-04-19
 * description    : 상품 커스텀 interface
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-19        rhkdg       최초 생성
 */
public interface ProductCustomRepository {

    /**
     * 목록 페이징 처리
     * @param searchDto
     * @return
     * @throws Exception
     */
    Page<ProductSummaryResponse> selectProductPageList(ProductDefaultDto searchDto) throws Exception;

    /**
     * 블랙 프라이데이 적용 목록 (페이징 ㅇ)
     * @param searchDto
     * @return
     * @throws Exception
     */
    Page<ProductSummaryResponse> selectProductPageListWithBlackFriday(ProductDefaultDto searchDto) throws Exception;

    /**
     * 블랙
     * @param searchDto
     * @return
     * @throws Exception
     */
    List<ProductTempResponse> selectProductPageWithItemTempList(ProductDefaultDto searchDto) throws Exception;


    /**
     * 목록 페이지
     * @param searchDto
     * @return
     * @throws Exception
     */
    List<ProductSummaryResponse> selectProductList(ProductDefaultDto searchDto) throws Exception;

    /**
     * 상품 상세 조회
     * @param dto
     * @return
     * @throws Exception
     */
    ProductDto selectProduct(ProductDto dto) throws Exception;

    /**
     * 상품 옵션 정보 조회
     * @param searchDto
     * @return
     * @throws Exception
     */
    List<ProductItemDto> selectProductItemList(ProductDefaultDto searchDto) throws Exception;

    /**
     * 상품 옵션 상세 정보
     * @param dto
     * @return
     * @throws Exception
     */
    ProductItemDto selectProductItem(ProductItemDto dto) throws Exception;

    /**
     * 상품 옵션 정보 업데이트
     * @param itemDto
     * @return
     * @throws Exception
     */
    boolean updateProductItemPcnt(ProductItemDto itemDto) throws Exception;
}
