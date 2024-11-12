# java-convenience-store-precourse

## 핵심 기능

구매자의 할인 혜택과 재고 상황을 고려하여 최종 결제 금액을 계산하고 안내하는 결제 시스템을 구현합니다.

## 기능 구현 목록

### 입력

#### 상품 목록 입력

- [x] ``src/main/resources/products.md`` 파일에서 상품 목록을 불러옵니
    - 상품 목록 데이터 목록 -> name,price,quantity,promotion
    - 각 목록을 ","로 구분합니다.


- 상품명
    - [x] 상품명이 빈 값인 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``


- 가격
    - [x] 가격이 빈 값인 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``
    - [x] 가격이 0보다 작은 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 가격은 0 이상이어야 합니다.``
    - [x] 가격이 숫자가 아닌 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``


- 수량
    - [x] 수량이 빈 값인 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``
    - [x] 수량이 0보다 작은 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 수량은 0 이상이어야 합니다.``
    - [x] 수량이 숫자가 아닌 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``


- 프로모션
    - [x] 프로모션 이름이 빈 값인 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``

- 파일 내용의 형식을 유지한다면 값은 수정할 수 있습니다.

#### 프로모션 목록 입력

- [x] ``src/main/resources/promotions.md`` 파일에서 프로모션 목록을 불러옵니다.
    - 상품 목록 데이터 목록 -> name,buy,get,start_date,end_date


- 프로모션
    - [x] 프로모션 이름이 빈 값인 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``


- Buy
    - [x] Buy가 빈 값인 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``
    - [x] Buy가 0보다 작은 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] Buy은 0 이상이어야 합니다.``
    - [x] Buy가 숫자가 아닌 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``


- Get
    - [x] Get이 빈 값인 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``
    - [x] Get이 0보다 작은 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] Get은 0 이상이어야 합니다.``
    - [x] Get이 숫자가 아닌 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``


- 파일 내용의 형식을 유지한다면 값은 수정할 수 있습니다.


#### 구매 상품 & 수량 입력

- [x] 구매할 상품명과 수량을 입력받습니다.


- ``구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])``


- 상품명, 수량은 하이픈(-)으로, 개별 상품은 대괄호([])로 묶어 쉼표(,)로 구분합니다.


- 입력 값
    - [x] 입력 값을 "," 구분자를 이용하여 입력 받은 문자열을 개별 상품으로 분리합니다.
    - [x] 입력 값이 빈 값인 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``


- 개별 상품
    - [x] 개별 상품을 "-" 구분자를 이용하여 상품명과 수량으로 분리합니다.
        - 하이픈으로 구분한 앞 문자열은 상품명, 뒷 문자열은 수량을 의미합니다.
    - [x] 개별 상품이 빈 값인 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``
    - [x] 개별 상품이 []로 묶여 있지 않은 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``
    - [x] 개별 상품 내 하이픈 구분자가 존재하지 않은 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``


- 상품명
    - [x] 상품명이 빈 값인 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``
    - [x] 상품명이 상품 목록에 존재하지 않는 경우 에러 메세지를 출력합니다.
        - ``[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.``

- 수량
    - [x] 수량이 빈 값인 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``
    - [x] 수량이 0보다 작은 경우 예외를 발생시키고 에러 메시지를 출력합니다.
        - ``[ERROR] 수량은 0 이상이어야 합니다.``
    - [x] 수량이 재고 수량보다 많은 경우 에러 메시지를 출력합니다.
        - ``[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.``


- [x] 입력 값이 올바르지 않은 경우 에러 메세지를 출력하고 다시 입력받습니다.

#### 기타 입력

- [x] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 그 수량만큼 추가 여부를 입력받는다.
    - ``현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)``
    - [x] "Y" 또는 "N"만 입력 가능합니다.
    - [x] 입력 값이 올바르지 않은 경우 에러 메세지를 출력하고 다시 입력받습니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``


- [x] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부를 입력받는다.
    - ``현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)``
    - [x] "Y" 또는 "N"만 입력 가능합니다.
    - [x] 입력 값이 올바르지 않은 경우 에러 메세지를 출력하고 다시 입력받습니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``


- [x] 멤버십 할인 적용 여부를 입력 받는다.
    - ``멤버십 할인을 받으시겠습니까? (Y/N)``
    - [x] "Y" 또는 "N"만 입력 가능합니다.
    - [x] 입력 값이 올바르지 않은 경우 에러 메세지를 출력하고 다시 입력받습니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``


- [x] 추가 구매 여부를 입력 받는다.
    - ``감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)``
    - [x] "Y" 또는 "N"만 입력 가능합니다.
    - [x] 입력 값이 올바르지 않은 경우 에러 메세지를 출력하고 다시 입력받습니다.
        - ``[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.``

### 재고 관리

- [x] 재고 수량을 고려하여 결제 가능 여부를 확인합니다.


- [x] 결제된 수량만큼 해당 상품의 재고에서 차감합니다.


- [x] 상품 구매를 완료 후 ``src/main/resources/products.md`` 파일을 최신 재고 상태로 업데이트 합니다.

### 멤버십

- [x] 멤버십 할인 적용 여부를 확인합니다.


- [x] 맴버십 회원은 프로모션 미적용 금액의 30%를 할인 받습니다.


- [x] 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용합니다.


- [x] 멤버십 할인의 최대 한도는 8,000원 입니다.

### 프로모션

- [x] 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인을 적용합니다.
    - 프로모션은 N개 구매 시 1개 무료 증정 (Buy N Get 1 Free)의 형태로 진행됩니다.
    - 1+1 또는 2+1 프로모션이 각각 지정된 상품에 적용되며, 동일 상품에 여러 프로모션이 적용되지 않습니다.


- [x] 프로모션 혜택은 프로모션 재고 내에서만 적용할 수 있습니다.


- [x] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내합니다.


- [x] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수향에 대해 정가로 결제하게 됨을 안내합니다.

### 계산

- [x] 총 구매액을 계산합니다.


- [x] 행사 할인 금액을 계산합니다.


- [x] 멤버십 여부에 따라서 멤버십 할인 금액을 계산합니다.


- [x] 총 구매액에서 할인 금액을 제외한 내실돈을 계산합니다.

### 출력

#### 상품 재고 목록 출력

- [x] 환영 인사와 함께 상품명, 가격, 프로모션 이름, 재고를 안내한다. 만약 재고가 0개라면 재고 없음을 출력한다.
  ```text
  안녕하세요. W편의점입니다.
  현재 보유하고 있는 상품입니다.
  
  - 콜라 1,000원 10개 탄산2+1
  - 콜라 1,000원 10개
  - 사이다 1,000원 8개 탄산2+1
  - 사이다 1,000원 7개
  - 오렌지주스 1,800원 9개 MD추천상품
  - 오렌지주스 1,800원 재고 없음
  - 탄산수 1,200원 5개 탄산2+1
  - 탄산수 1,200원 재고 없음
  - 물 500원 10개
  - 비타민워터 1,500원 6개
  - 감자칩 1,500원 5개 반짝할인
  - 감자칩 1,500원 5개
  - 초코바 1,200원 5개 MD추천상품
  - 초코바 1,200원 5개
  - 에너지바 2,000원 5개
  - 정식도시락 6,400원 8개
  - 컵라면 1,700원 1개 MD추천상품
  - 컵라면 1,700원 10개
  
  ```

#### 영수증 출력

- [x] 영수증은 고객의 구매 내역과 할인을 요약하여 출력한다.

- 영수증 항목은 아래와 같다.
    - 구매 상품 내역: 구매한 상품명, 수량, 가격
    - 증정 상품 내역: 프로모션에 따라 무료로 제공된 증정 상품의 목록
    - 금액 정보
    - 총구매액: 구매한 상품의 총 수량과 총 금액
    - 행사할인: 프로모션에 의해 할인된 금액
    - 멤버십할인: 멤버십에 의해 추가로 할인된 금액
    - 내실돈: 최종 결제 금액
    - 영수증의 구성 요소를 보기 좋게 정렬하여 고객이 쉽게 금액과 수량을 확인할 수 있게 한다.

  ```text
  ==============W 편의점================
  상품명		수량	금액
  콜라		3 	3,000
  에너지바 	5 	10,000
  =============증	정===============
  콜라		1
  ====================================
  총구매액		8	13,000
  행사할인			-1,000
  멤버십할인		-3,000
  내실돈			 9,000
  ```


