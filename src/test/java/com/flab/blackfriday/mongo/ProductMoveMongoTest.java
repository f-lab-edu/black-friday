package com.flab.blackfriday.mongo;

import com.flab.blackfriday.modules.product.coupon.dto.ProductCouponDto;
import com.flab.blackfriday.modules.product.domain.Product;
import com.flab.blackfriday.modules.product.repository.ProductRepository;
import com.flab.blackfriday.mongo.modules.product.mgrepository.ProductMGRepository;
import com.flab.blackfriday.mongo.modules.product.service.ProductMGService;
import org.aspectj.bridge.IMessageHandler;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * packageName    : com.flab.blackfriday.mongo
 * fileName       : ProductMoveMongoTeset
 * author         : rhkdg
 * date           : 2024-06-29
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-29        rhkdg       최초 생성
 */
@SpringBootTest
@ActiveProfiles({"local"})
public class ProductMoveMongoTest {

    @Autowired
    private ProductMGRepository productMGRepository;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("몽고 디비로 RDM 상품 정보 이전 시간 확인 테스트")
    @Test
    void test() {

            StringBuilder str = new StringBuilder();

            long beforeTime = System.currentTimeMillis();

            //given RDB 상품 가져오기
            List<Product> list = productRepository.findAll();

            long afterTime = System.currentTimeMillis();
            long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
            str.append("RDB Product all select list 시간 ms : ").append(secDiffTime).append(" , ");

            beforeTime = System.currentTimeMillis();

            for( com.flab.blackfriday.modules.product.domain.Product response : list){
                com.flab.blackfriday.mongo.modules.product.document.Product product = new com.flab.blackfriday.mongo.modules.product.document.Product();
                product.setPNum(response.getPNum());
                product.setTitle(response.getPTitle());
                product.setCategCd(response.getCategory().getCategCd());
                product.setContent(response.getPContent());
                product.setOrd(response.getOrd());
                product.setPopulYn(response.getPopulYn());
                product.setUseYn(response.getUseYn());
                productMGRepository.insert(product);
            }

            afterTime = System.currentTimeMillis();
            secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
            str.append("MongoDB all insert Product  시간 ms : ").append(secDiffTime).append(" , ");

            beforeTime = System.currentTimeMillis();

            List<com.flab.blackfriday.mongo.modules.product.document.Product> list2 = productMGRepository.findAll();

            afterTime = System.currentTimeMillis();
            secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
            str.append("MongoDB all select list  시간 ms : ").append(secDiffTime);

            System.out.println(str.toString());
    }
    
    @DisplayName("RDB 와 MongoDB 조회 속도 비교")
    @Test
    void selectTest() {

        StringBuilder str = new StringBuilder();

        long beforeTime = System.currentTimeMillis();
        //given RDB 상품 가져오기
        productRepository.findAll();

        long afterTime = System.currentTimeMillis();
        long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
        str.append("RDB Product all select list 시간 ms : ").append(secDiffTime).append(" , ");

        beforeTime = System.currentTimeMillis();
        productMGRepository.findAll();

        afterTime = System.currentTimeMillis();
        secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
        str.append("MongoDB all select list  시간 ms : ").append(secDiffTime);

    }
}
