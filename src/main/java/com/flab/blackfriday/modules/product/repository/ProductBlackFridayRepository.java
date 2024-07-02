package com.flab.blackfriday.modules.product.repository;

import com.flab.blackfriday.modules.product.domain.ProductBlackFriday;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.flab.blackfriday.product.repository
 * fileName       : ProductBlackFriday
 * author         : GAMJA
 * date           : 2024/04/21
 * description    : 블랙 프라이데이 repository
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/21        GAMJA       최초 생성
 */
public interface ProductBlackFridayRepository extends JpaRepository<ProductBlackFriday,Long>,ProductCustomBlackFridayRepository {
}
