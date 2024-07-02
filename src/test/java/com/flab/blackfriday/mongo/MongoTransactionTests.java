package com.flab.blackfriday.mongo;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * packageName    : com.flab.blackfriday.mongo
 * fileName       : MongoTransactionTests
 * author         : rhkdg
 * date           : 2024-06-27
 * description    : 몽고디비 transaction test
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-27        rhkdg       최초 생성
 */
@SpringBootTest
@ActiveProfiles({"local"})
public class MongoTransactionTests {


    @DisplayName(value = "몽고디비 레플리카를 적용한 트랜잭션 테스트 입니다.")
    @Order(1)
    @Test
    void trasactionTest() {

    }
}
