package com.flab.blackfriday.modules.order.service;

import com.flab.blackfriday.modules.order.dto.OrderDto;
import com.flab.blackfriday.modules.product.domain.Product;
import com.flab.blackfriday.modules.product.repository.ProductRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.flab.blackfriday.order.service
 * fileName       : OrderLockService
 * author         : rhkdg
 * date           : 2024-06-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-02        rhkdg       최초 생성
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderLockService {

    private final OrderService orderService;

    private final ProductRepository productRepository;
    
    private final CacheManager cacheManager;

    /**
     * 주문 처리 낙관적 lock test (비동기를 적용한 예시)
     * @param dto
     * @throws Exception
     */
    @Transactional
    @Async
    public void insertOrderOptimisticLock(OrderDto dto) throws Exception {
        while(true) {
            try{
                //db lock
                Product product = productRepository.selectProductoptimisticLock(dto.getPNum());
                orderService.insertOrderOptimisticLock(dto,product);
                //마지막에는 for update이기 떄문에 무조건 업데이트 진행
                productRepository.save(product);
            }catch (OptimisticLockException e) {
                log.error("### 낙관적 락 버전 불일치 발생 ### {}",e.getMessage());
            }
        }
    }

    /**
     * 비동기 및 캐싱을 이용하여 처리한 예시
     * @param dto
     * @throws Exception
     */
    @Transactional
    @Async
    public void insertOrderOptimisticLockAsync(OrderDto dto) throws Exception {

        //db lock
        Product product = productRepository.selectProductoptimisticLock(dto.getPNum());

        while(true) {
            try{
                orderService.insertOrderOptimisticLock(dto,product);
                //마지막에는 for update이기 떄문에 무조건 업데이트 진행
                product = productRepository.save(product);
            }catch (OptimisticLockException e) {
                log.error("### 낙관적 락 버전 불일치 발생 ### {}",e.getMessage());
                product = productRepository.selectProductoptimisticLock(dto.getPNum());
            }
        }
    }

    /**
     * 비동기와 캐싱을 추가한 예시
     * @param dto
     * @throws Exception
     */
    @Transactional
    @Async
    public void insertOrderOptimisticLockAsyncCache(OrderDto dto) throws Exception {

        //db lock
        Product product = productRepository.selectProductoptimisticLock(dto.getPNum());

        while(true) {
            try{
                //캐싱 정보
                int cacheVersion = (int) cacheManager.getCache("productVersion").get(dto.getPNum()).get();
                //캐싱 정보가 불일치 할 경우 예외처리
                if(cacheVersion != product.getVersion()) {
                    throw new OptimisticLockException("캐싱 product version 불일치");
                }

                orderService.insertOrderOptimisticLock(dto,product);
                //마지막에는 for update이기 떄문에 무조건 업데이트 진행
                product = productRepository.save(product);

                // 업데이트 후에는 캐시 갱신
                cacheManager.getCache("productVersion").put(dto.getPNum(), product.getVersion());

            }catch (OptimisticLockException e) {
                log.error("### 낙관적 락 버전 불일치 발생 ### {}",e.getMessage());
                product = productRepository.selectProductoptimisticLock(dto.getPNum());
            }
        }
    }

    @Transactional
    @Async
    public void insertOrderOptimisticLockAsyncCacheThread(OrderDto dto) throws Exception {

        //db lock
        Product product = productRepository.selectProductoptimisticLock(dto.getPNum());

        while(true) {
            try{
                //캐싱 정보
                int cacheVersion = (int) cacheManager.getCache("productVersion").get(dto.getPNum()).get();
                //캐싱 정보가 불일치 할 경우 예외처리
                if(cacheVersion != product.getVersion()) {
                    throw new OptimisticLockException("캐싱 product version 불일치");
                }

                orderService.insertOrderOptimisticLock(dto,product);
                //마지막에는 for update이기 떄문에 무조건 업데이트 진행
                product = productRepository.save(product);

                // 업데이트 후에는 캐시 갱신
                cacheManager.getCache("productVersion").put(dto.getPNum(), product.getVersion());

            }catch (OptimisticLockException e) {
                log.error("### 낙관적 락 버전 불일치 발생 ### {}",e.getMessage());
                Thread.sleep(100);
                product = productRepository.selectProductoptimisticLock(dto.getPNum());
            }
        }
    }

    @Transactional
    @Async
    public void insertOrderOptimisticLockAsyncCacheNoLimit(OrderDto dto) throws Exception {

        //db lock
        Product product = productRepository.selectProductoptimisticLock(dto.getPNum());

        int count = 1;
        boolean flag = true;

        while(true) {
            try{
                if(count > 3){
                    flag = false;
                    break;
                }
                //캐싱 정보
                int cacheVersion = (int) cacheManager.getCache("productVersion").get(dto.getPNum()).get();
                //캐싱 정보가 불일치 할 경우 예외처리
                if(cacheVersion != product.getVersion()) {
                    throw new OptimisticLockException("캐싱 product version 불일치");
                }

                orderService.insertOrderOptimisticLock(dto,product);
                //마지막에는 for update이기 떄문에 무조건 업데이트 진행
                product = productRepository.save(product);

                // 업데이트 후에는 캐시 갱신
                cacheManager.getCache("productVersion").put(dto.getPNum(), product.getVersion());
            }catch (OptimisticLockException e) {
                log.error("### 낙관적 락 버전 불일치 발생 ### {}",e.getMessage());
                count++;
                Thread.sleep(100);
                product = productRepository.selectProductoptimisticLock(dto.getPNum());
            }
        }

        if(!flag) {
            throw new OptimisticLockException("3번의 재시도 결과 실패하였습니다.");
        }

    }

    @Transactional
    @Async
    public void insertOrderOptimisticLockAsyncCacheNoLimitv2(OrderDto dto) throws Exception {

        //db lock
        Product product = productRepository.selectProductoptimisticLock(dto.getPNum());

        int count = 1;
        boolean flag = true;

        while(true) {
            try{
                if(count > 3){
                    flag = false;
                    break;
                }
                //캐싱 정보
                int cacheVersion = (int) cacheManager.getCache("productVersion").get(dto.getPNum()).get();
                //캐싱 정보가 불일치 할 경우 예외처리
                if(cacheVersion != product.getVersion()) {
                    throw new OptimisticLockException("캐싱 product version 불일치");
                }

                orderService.insertOrderOptimisticLock(dto,product);
                //마지막에는 for update이기 떄문에 무조건 업데이트 진행
                product = productRepository.save(product);

                // 업데이트 후에는 캐시 갱신
                cacheManager.getCache("productVersion").put(dto.getPNum(), product.getVersion());
            }catch (OptimisticLockException e) {
                log.error("### 낙관적 락 버전 불일치 발생 ### {}",e.getMessage());
                count++;
                product = productRepository.selectProductoptimisticLock(dto.getPNum());
            }
        }

        if(!flag) {
            throw new OptimisticLockException("3번의 재시도 결과 실패하였습니다.");
        }

    }

}
