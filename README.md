# 프로젝트 소개
패스트캠퍼스 백엔드 과정에서 미니프로젝트 과제인 투자 서비스 REST API를 설계하고 구현했습니다.

# 요구사항
## 1. 기본 요구사항
- 사용자는 원하는 투자 상품을 투자할 수 있습니다.
- 투자상품이 오픈될 때, 다수의 고객이 동시에 투자를 합니다.
- 투자 후 투자상품의 누적 투자모집 금액, 투자자 수가 증가됩니다.
- 총 투자모집금액 달성 시 투자는 마감되고 상품은 sold out 됩니다.

## 2. API 목록 및 요구사항<br>
### 2-1. 투자 가능 상품 조회 API <br>
- 상품 모집기간내(투자 시작일시 ~ 투자 종료일시)의 상품만 응답합니다.
- Response에는 아래의 값을 포함합니다.
  - 상품 ID
  - 상품명
  - 총 모집금액
  - 현재 모집금액
  - 투자자 수
  - 상품 모집기간(투자 시작일시, 투자 종료일시)

### 2-2. 투자하기 API
- Request에는 아래의 값을 포함합니다.
  - 사용자 식별값
  - 상품 ID
  - 투자 금액
- 총 투자모집금액이 남아 있지 않다면 실패 상태를 응답합니다.
- 투자요청한 금액이 투자가능금액보다 클 경우 실패로 처리힙니다.

### 2-3.나의 투자상품 조회 API
- 내가 투자한 모든 상품을 반환합니다.(취소한 상품도 조회 가능)
- Response에는 아래의 값을 포함합니다.
  -	투자 ID
  - 상품 ID
  - 상품명
  - 총 모집금액
  - 나의 투자금액
  - 투자상태
  - 투자일시

### 2-4. 투자취소하기 API
- Request에는 아래의 값을 포함합니다.
  -	사용자 식별값
  - 상품ID
- 취소가 되더라도 상품의 상태는 변경되지 않습니다.
- 취소가 되더라도 나의 투자상품 조회에서는 취소 조회가 되어야합니다.

## 3. 공통사항<br>
-	요청한 사용자 식별값은 숫자 형태이며 “X-USER_ID”라는 HTTP Header로 전달됩니다.
-	채점용 테스트코드 외에 단위테스트를 작성합니다.
-	Request/Response JSON은 채점용 테스트코드를 참고합니다.
-	각 투자상품의 필요한 정보는 아래와 같습니다.
     (상품명, 총 투자모집금액, 투자 시작일시, 투자 종료일시)

## 4. 기술 요구 사항
* 전달해준 프로젝트에서 작성합니다.<br>
* SpringBoot + H2로 환경에서 시작합니다.<br>
* 기본적으로 필요한 기술은<br>
* Java, REST API, Spring boot, JPA, H2이며 스스로 판단하여 추가적인 기술은 추가하여 사용합니다.<br>

# 프로젝트 설계 및 구현

## 1. 사용기술
- 언어 : Java
- 프레임워크 : Spring Boot
- 데이터베이스 : H2(In Memory)
- 빌드: Gradle
- 테스트 : Junit

## 2. 아키텍처
- 계층형 아키텍처 사용
  <img width="750" alt="architecture" src="https://user-images.githubusercontent.com/35022991/155467478-95cae9e7-fa33-4aab-b275-7e18d2a3b484.png">

## 3. 예외처리
- @ControllerAdvice 사용
  <img width="600" alt="exception_handling" src="https://user-images.githubusercontent.com/35022991/155537760-49d7862f-b490-44c5-a555-60b751f3d78a.png">

## 4. API
아래 링크를 클릭하시면 Spring REST Docs로 생성한 API문서를 볼 수 있습니다.  
[투자서비스 API 문서](https://drive.google.com/file/d/1v27maY2fDGxrSKX0v1g7F4jFPKFLe-3z/view?usp=sharing)

## 5. 테스트
- 통합테스트 및 단위테스트 작성
- 테스트 커버리지 100% 달성  

![test_results](https://user-images.githubusercontent.com/35022991/155540214-47167e55-615d-4fa1-9d1e-ebadde827b67.png)
