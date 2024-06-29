package com.flab.blackfriday.mongo.modules.product.mgrepository;

import com.flab.blackfriday.mongo.modules.product.document.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName    : com.flab.blackfriday.mongo.modules.product.mgrepository
 * fileName       : ProductRepository
 * author         : rhkdg
 * date           : 2024-06-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-27        rhkdg       최초 생성
 */
@Repository
public interface ProductMGRepository extends MongoRepository<Product,String> {
}
