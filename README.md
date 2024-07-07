# Feature/Performance Branch 정보

## 주요내용
+ `성능 테스트`
  + Docker 서버에 올려 테스트
+ 할인 쿠폰 발급 및 사용 API 구축


## 작업기간
`2024-05-20 ~ 2024-05-27`


## 성능테스트를 위한 환경세팅
+ 성능테스트 공간
  + 로컬 Window Docker 서버에 서비스 구축
+ 이커머스 환경상 상품의 개수가 많아야 된다고 판단
  + Product table(상품 테이블)
  + Product item table(상품 옵션 테이블)
  + Product BlackFriday table(상품 블랙프라이데이 할인 테이블)
    + 에 대한 dump 데이터 각각에 10000개의 dump 데이터를 밀어 넣었음 [참조 사이트](https://www.mockaroo.com/)


## 성능테스트 도구

### grafana K6 도입
### `nGrinder`가 아닌 `k6`를 왜 선택하였는가?
JVM과 유사한 언어를 사용하는 nGrinder를 이용하여 성능을 테스트할 수 있겠지만 nGrinder는 Groovy로 스크립트를 이용하는 시스템입니다.
우선적으로 제일 큰것은 Groovy를 사용해본적이 없어 익숙하지 않다는 점에서 시간적인 비용이 많이 발생할 수 있겠다고 판단하였습니다.
반면에, k6는 JS 기반 스크립트를 사용하는 언어이기 때문에 좀 더 익숙한 언어에 근접하여 선택하게 되었습니다. 그리고 구체적인 스크립트 구조를 생성할 수 있다는 장점이 있어 선택하게 되었습니다.

### `grafana,influxdb` 모니터링 시각화 도입
k6만으로 결과값을 얻어 볼수 있지만 실시간 유입 정보들을 확인하기 위한 모니터링을 통한 경험을 해보고자 하였습니다.


## 성능 테스트시 보았던 요소들
### 하드웨어 자원의 소모
+ CPU 사용률의 % 크기가 어떻게 이루어질지 확인
+ RAM 메모리 소모는 어느정도 발생하는가? 에 대한 확인
+ DISK 공간 사용에 대한 확인

### 데이터베이스의 성능
+ 데이터베이스 요청에 따른 속도 이슈 확인


## 성능 테스트 상황설정
* [성능테스트 Part1]([https://github.com/f-lab-edu/black-friday/wiki/%EC%84%B1%EB%8A%A5-%ED%85%8C%EC%8A%A4%ED%8A%B8-Part-1-(%EC%83%81%ED%92%88-%EC%A1%B0%ED%9A%8C-%ED%85%8C%EC%8A%A4%ED%8A%B8)](https://github.com/f-lab-edu/black-friday/wiki/%EC%84%B1%EB%8A%A5-%ED%85%8C%EC%8A%A4%ED%8A%B8-Part(1)-%E2%80%90-%EC%83%81%ED%92%88-%EC%A1%B0%ED%9A%8C-%ED%85%8C%EC%8A%A4%ED%8A%B8))
* [성능테스트 Part2]([https://github.com/f-lab-edu/black-friday/wiki/%EC%84%B1%EB%8A%A5-%ED%85%8C%EC%8A%A4%ED%8A%B8-Part2-(%EC%A3%BC%EB%AC%B8-%EC%B2%98%EB%A6%AC-%ED%85%8C%EC%8A%A4%ED%8A%B8)](https://github.com/f-lab-edu/black-friday/wiki/%EC%84%B1%EB%8A%A5-%ED%85%8C%EC%8A%A4%ED%8A%B8-Part(2)-%E2%80%90-%EC%A3%BC%EB%AC%B8-%EC%B2%98%EB%A6%AC-%ED%85%8C%EC%8A%A4%ED%8A%B8))
