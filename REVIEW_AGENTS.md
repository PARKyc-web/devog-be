# devog 리팩토링 평가 요청

아래 프로젝트를 코드 리뷰하듯 냉정하게 평가해줘.  
칭찬보다 개선점을 우선해줘. “작동한다”는 이유로 좋게 평가하지 말고, 실무 백엔드 코드 관점에서 유지보수성, 책임 분리, 테스트 가능성을 기준으로 봐줘.

## 평가 대상

- 프로젝트: devog
- 평가 목적: DDD 스타일 일부 적용, 책임 분리, 테스트 가능한 구조 학습
- 중점 평가 범위:
    - Controller / Application Service / Domain / Repository 책임 분리
    - Request / Command / Query / Response / Result DTO 분리
    - Entity의 책임 범위
    - 비즈니스 규칙의 위치
    - 예외 처리 구조
    - 테스트 가능성
    - 과한 설계 여부

## 평가 기준표

총점 100점으로 평가해줘.

### 1. 계층 책임 분리 - 15점

- Controller가 HTTP 요청/응답 처리에만 집중하는가
- ApplicationService 또는 Facade가 유스케이스 흐름을 조립하는가
- Repository가 저장/조회 책임만 가지는가
- Entity/Domain이 자기 규칙을 가지는가
- 특정 계층이 다른 계층의 책임을 침범하지 않는가

감점 예:
- Controller에 비즈니스 로직 존재
- Service가 요청 DTO, 응답 DTO, Entity 변환, 계산, 저장을 모두 처리
- Repository에 비즈니스 판단 로직 존재

### 2. DTO 설계 - 12점

- Request와 Response가 명확히 분리되어 있는가
- 상태 변경 유스케이스에서 Request를 Command로 변환하는가
- 조회 조건이 복잡한 경우 Query/Condition으로 분리했는가
- Service가 Controller Request DTO에 직접 의존하지 않는가
- Entity가 Response DTO를 알지 않는가

감점 예:
- Request DTO를 Service까지 그대로 전달
- Entity에 `toResponse()`, `toInfo()` 같은 메서드 존재
- 하나의 DTO 클래스 내부에 Request/Response/Entity 변환/계산 로직이 섞임

### 3. 도메인 모델링 - 15점

- Entity가 단순 테이블 매핑 객체에 머물지 않는가
- Entity 생성 시 유효한 상태를 보장하는가
- 정적 팩토리 메서드나 의미 있는 생성 메서드를 사용하는가
- 도메인 규칙이 적절한 위치에 있는가
- Value Object로 분리할 만한 개념을 primitive/String/BigDecimal로 방치하지 않았는가

감점 예:
- Entity가 setter로 무제한 변경 가능
- Service에서 모든 도메인 규칙을 처리
- Entity factory가 검증 없이 필드만 채움
- 도메인 개념이 이름 없는 private method로만 숨어 있음

### 4. 유스케이스 흐름 설계 - 12점

- ApplicationService/Facade 메서드를 읽으면 업무 흐름이 보이는가
- 세부 계산/정책 로직이 별도 객체로 분리되어 있는가
- private method와 별도 클래스 분리 기준이 적절한가
- 트랜잭션 경계가 유스케이스 단위로 잡혀 있는가

감점 예:
- 하나의 service method가 검증, 조회, 계산, 저장, 응답 변환을 모두 수행
- 독립 테스트 가능한 계산 로직이 private method에 묻혀 있음
- 조회와 계산이 한 메서드에 결합됨

### 5. 예외 처리 - 10점

- 공통 예외 처리 구조가 있는가
- 도메인별 ErrorCode 또는 BaseErrorCode 구조가 적절한가
- 핵심 비즈니스 실패가 구체 예외로 표현되는가
- HTTP status와 비즈니스 error code가 분리되어 있는가
- 예외 메시지와 응답 메시지 정책이 일관적인가

감점 예:
- 모든 예외를 `RuntimeException` 또는 범용 커스텀 예외 하나로만 처리
- ErrorCode가 한 파일에 무분별하게 커짐
- 예외에 상세 메시지를 넣고 응답에서는 버림
- 500으로 처리하면 안 되는 비즈니스 오류를 500으로 처리

### 6. 테스트 가능성 및 테스트 품질 - 15점

- 핵심 도메인 규칙이 단위 테스트 가능한 구조인가
- 외부 API, DB 없이 테스트 가능한 계산/정책 객체가 있는가
- Controller 통합 테스트와 Service/Domain 단위 테스트가 구분되어 있는가
- 실패 케이스 테스트가 충분한가
- 테스트 이름이 요구사항을 설명하는가

감점 예:
- 테스트가 거의 없음
- Controller 테스트만 있고 핵심 계산 단위 테스트 없음
- Mock이 과도해서 실제 로직 검증이 약함
- 성공 케이스만 테스트

### 7. 트랜잭션과 영속성 설계 - 8점

- 쓰기 유스케이스에 `@Transactional`이 적절히 적용되어 있는가
- 조회 유스케이스에 `@Transactional(readOnly = true)`가 적용되어 있는가
- Entity와 Repository 사용이 계층 경계를 침범하지 않는가
- 조회 성능상 명백한 N+1 또는 반복 쿼리 문제가 없는가

감점 예:
- 트랜잭션 경계가 Repository에만 의존
- Service/Facade에서 트랜잭션 의도가 드러나지 않음
- Controller가 Entity를 직접 다룸

### 8. 네이밍과 패키지 구조 - 8점

- 클래스 이름이 역할을 정확히 설명하는가
- `ServiceImpl`, `Manager`, `Processor`, `Util` 같은 모호한 이름을 남발하지 않는가
- `Facade`라는 이름이 실제 조합 역할에 맞게 쓰였는가
- 패키지가 기술 계층과 도메인 경계를 잘 드러내는가

감점 예:
- 구현체가 하나뿐인데 관성적으로 interface + Impl 사용
- 모든 DTO를 한 패키지 또는 한 클래스 내부에 몰아넣음
- `CommonUtil`, `BusinessService`, `DataProcessor`처럼 의미가 흐린 이름 사용

### 9. 과한 설계 여부 - 5점

- 프로젝트 규모에 비해 불필요하게 추상화하지 않았는가
- 단순 조회까지 과도하게 Command/Query/Mapper/Facade로 쪼개지 않았는가
- 현재 요구사항 기준으로 납득 가능한 분리인가

감점 예:
- 구현체가 하나인데 무의미한 인터페이스 남발
- 단순 필드 전달만 하는 클래스가 지나치게 많음
- 설계 패턴을 적용하기 위해 오히려 가독성이 떨어짐

## 최종 출력 형식

다음 형식으로 답변해줘.

1. 총점: `XX / 100`
2. 한줄 평가: 실무 기준으로 현재 코드의 수준을 냉정하게 요약
3. 가장 큰 문제 5개
    - 파일/클래스 위치를 근거로 설명
    - 왜 문제인지
    - 어떻게 고치면 좋은지
4. 잘한 점 3개
    - 단, 과장하지 말고 실제 장점만
5. 우선순위별 개선 작업
    - P1: 반드시 고칠 것
    - P2: 고치면 구조가 좋아지는 것
    - P3: 여유 있을 때 개선할 것
6. 리팩토링 방향 제안
    - 패키지 구조
    - 주요 클래스 이름
    - DTO 흐름
    - 테스트 전략
7. 면접/과제 평가 관점에서 감점될 부분
8. 다음 리팩토링 단계에서 내가 집중해야 할 학습 포인트

평가는 냉정하게 해줘.  
“작동은 한다”는 표현은 좋은 평가 근거로 쓰지 말고, 유지보수성과 테스트 가능성을 기준으로 봐줘.
