#### 2024-11-11

##### Chores

* **gitignore:**  gitignore 수정 (f7c80492)

##### Documentation Changes

* **Study:**  주석 공부법 적용 및 리펙토링 사항 확인 (6393c80a)
* **README:**  편의점 기능 목록 작성 (089b4056)

##### New Features

* **AppConfig:**  AppConfig 생성 (e8ede7b6)
* **Bill:**  구매 영수증 생성 및 출력 기능 구현 (9bfab51b)
* **OrderService:**
  *  다른 상품 구매 희망 시 반복 실행 기능 구현 (7ebca708)
  *  구매 수량 재고에서 차감 기능 구현 (ef58ad55)
  *  주문 상품 프로모션 적용 기능 구현 (bab3a3ff)
* **BillingService:**  계산서 생성 및 멤버쉽 적용 여부 입력 구현 (bbfd57ea)
* **Order:**  구매 상품 및 수량 입력 기능 구현 (c57d0f8b)
* **PromotionRepository:**  프로모션 목록 입력 기능 구현 (8eb07932)
* **OutputView:**  현재 재고 목록 출력 (932b711a)
* **StorkRepository:**  StorkRepository 구현 (f015b4ba)
* **FileHandler:**  파일 업로드 기능 구현 (319b3463)

##### Refactors

* **Discount:**  할인 정책 Enum으로 관리하게 변경 (449302aa)
* **StockPattern:**  checkPromotionName 메서드 분리 (b2c622b7)
* **BillingService:**  Bill 생성 흐름 관리 책임 위임 (c0001458)
* **StockService:**
  *  재고 업데이트 관련 책임 위임 (1f1a452a)
  *  재고 관리 기능 StockService로 분리 (db23606a)
* **StoreController:**  run() 메서드 분리 (6be94ffd)
* **Answer:**  상수 처리 (9fd3348a)
* **DiscountPolicy:**  할인 정책 다형성 적용 (984a4a0d)
* **Application:**  전체 코드 내 상수 처리 진행 (363b03e2)
* **repository:**  data 관련 기능 메서드 분리 및 상수처리 (e2b28544)

##### Code Style Changes

* **Application:**  코드 컨벤션 확인 및 적용 (55995e37)

##### Tests

* **StockServiceTest:**  StockService 테스트 추가 (c80a7e2c)
* **BillingServiceTest:**  BillingService 테스트 추가 (2d06d73e)
* **OrderServiceTest:**  OrderService 테스트 추가 (48c4791e)
* **PromotionRepositoryTest:**  프로모션 저장소 테스트 추가 (a1db9883)
* **StockRepository:**  StockRepository 테스트 추가 (d2dc5d89)
* **DiscountPolicyTest:**  할인 정책 테스트 추가 (bf45c235)
* **CustomApplicationTest:**  편의점 기능 테스트 추가 (75d81057)
* **OrdersTest:**  전체 주문 테스트 추가 (3bdc2502)
* **Order:**  주문 테스트 추가 (bae967a4)
* **StockTest:**  검증 테스트와 기능 테스트 분리 (73d53f74)
* **PromotionTest:**
  *  프로모션 기능 테스트 추가 (1fce514e)
  *  Promotion test 작성 (2443fa80)
* **Stock:**  Stock test 작성 (4e79cab5)
* **FileHandlerTest:**  파일 입력 테스트 (e40f9594)

