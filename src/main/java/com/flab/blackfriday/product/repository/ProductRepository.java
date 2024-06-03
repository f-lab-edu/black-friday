package com.flab.blackfriday.product.repository;

import com.flab.blackfriday.product.domain.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    /**
     * 상품 번호 생성
     * @return
     */
    @Query(value=" select CONVERT(IFNULL(MAX(p_num),concat(FORMATDATETIME(NOW(),'yyyyMMdd'),'00')),int)+1 as pNum  FROM product "+
            "	where p_num like concat(FORMATDATETIME(NOW(),'yyyyMMdd'),'%')", nativeQuery = true)
    String selectProductNumMax();

    /**
     * 상품 db 비관적 lock 
     * select * for update 방식
     * @param productNum
     * @return
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.pNum = :productNum")
    Product selectProductPessimisticLock(@Param("productNum") String productNum);

    /**
     * 상품 db 긍정적 lock
     * @param productNum
     * @return
     */
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("select p from Product p where p.pNum = :productNum")
    Product selectProductoptimisticLock(@Param("productNum") String productNum);

}
