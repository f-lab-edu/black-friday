package com.flab.blackfriday.product.repository;

import com.flab.blackfriday.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.flab.blackfriday.product.repository
 * fileName       : ProductRepository
 * author         : rhkdg
 * date           : 2024-04-19
 * description    : 상품 jpa repository
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-19        rhkdg       최초 생성
 */
public interface ProductRepository extends JpaRepository<Product,String>,ProductCustomRepository {
}
