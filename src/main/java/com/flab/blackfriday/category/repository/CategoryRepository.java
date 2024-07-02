package com.flab.blackfriday.category.repository;

import com.flab.blackfriday.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * packageName    : com.flab.blackfriday.category.repository
 * fileName       : CategoryRepository
 * author         : GAMJA
 * date           : 2024/04/19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/19        GAMJA       최초 생성
 */
public interface CategoryRepository extends JpaRepository<Category,String>,CategoryCustomRepository {

    /**
     * 부모코드와 Pk코드로 목록 조회
     * @param parentCd
     * @return
     */
    List<Category> findByParentCdAndCategCd(String parentCd,String categCd);
}
