# devog DDD 리팩토링 평가표

이 문서는 `devog` 프로젝트를 조금씩 DDD 스타일로 리팩토링하면서, 다른 Codex 세션이나 리뷰어에게 일관된 기준으로 평가받기 위한 체크리스트다.

평가 목적은 "잘 돌아가는 코드"를 확인하는 것이 아니라, 책임 분리, 도메인 모델링, 테스트 가능성, 과한 설계 여부를 냉정하게 점검하는 것이다.

## 리뷰 요청 프롬프트

아래 내용을 다른 세션에 그대로 붙여서 사용한다.

```markdown
devog 프로젝트를 코드 리뷰하듯 냉정하게 평가해줘.

현재 목표는 기존 코드를 한 번에 갈아엎는 것이 아니라, 기능 단위로 조금씩 DDD 스타일에 가깝게 개선하는 것이다.

칭찬보다 개선점을 우선해줘. "작동한다"는 이유로 좋게 평가하지 말고, 실무 백엔드 코드 관점에서 유지보수성, 책임 분리, 테스트 가능성을 기준으로 봐줘.

특히 아래 관점을 중점적으로 평가해줘.

- Controller / Application Service / Domain / Repository 책임 분리
- Request / Command / Query / Response / Result DTO 분리
- Entity의 책임 범위
- 비즈니스 규칙의 위치
- 예외 처리 구조
- 트랜잭션 경계
- 테스트 가능성
- 과한 설계 여부

평가 결과는 총점 100점 기준으로 알려줘.
```

## 총점 기준

총점은 100점이다.

| 항목 | 배점 |
| --- | ---: |
| 계층 책임 분리 | 15 |
| DTO 및 입출력 모델 설계 | 12 |
| 도메인 모델링 | 15 |
| 유스케이스 흐름 설계 | 12 |
| 예외 처리 | 10 |
| 테스트 가능성 및 테스트 품질 | 15 |
| 트랜잭션과 영속성 설계 | 8 |
| 네이밍과 패키지 구조 | 8 |
| 과한 설계 여부 | 5 |

## 1. 계층 책임 분리 - 15점

확인할 것:

- Controller는 HTTP 요청/응답 처리에만 집중하는가
- Application Service 또는 Facade는 유스케이스 흐름을 조립하는가
- Repository는 저장/조회 책임만 가지는가
- Entity와 Domain 객체는 자기 규칙을 가지는가
- 특정 계층이 다른 계층의 책임을 침범하지 않는가

감점 예:

- Controller에 비즈니스 로직이 있다
- Service가 요청 DTO, 응답 DTO, 계산, 검증, 저장, Entity 변환을 모두 처리한다
- Repository에 비즈니스 판단 로직이 있다
- Entity가 API 응답 DTO를 알고 있다

## 2. DTO 및 입출력 모델 설계 - 12점

확인할 것:

- Request와 Response가 명확히 분리되어 있는가
- 상태 변경 유스케이스에서 Request를 Command로 변환하는가
- 조회 조건이 복잡한 경우 Request를 Query 또는 Condition으로 변환하는가
- Service가 Controller Request DTO에 직접 의존하지 않는가
- Response DTO는 Entity를 외부에 직접 노출하지 않는가
- DTO는 값 전달 책임에 집중하는가

감점 예:

- Request DTO를 Service까지 그대로 전달한다
- 하나의 DTO 클래스 내부에 Request, Response, Entity 변환, 계산 로직이 섞여 있다
- Entity에 `toResponse()`, `toInfo()` 같은 DTO 변환 메서드가 있다
- DTO가 `toEntity()`를 통해 Entity 생성 규칙을 가져간다

참고 기준:

- `Request`: HTTP 요청 모델
- `Command`: 상태 변경 유스케이스 입력 모델
- `Query` 또는 `Condition`: 조회 유스케이스 입력 모델
- `Result` 또는 `Info`: Application Service 반환 모델
- `Response`: HTTP 응답 모델

단순 조회는 Query 객체를 억지로 만들 필요 없다. 예를 들어 `findById(id)`, `findLatest(currency)` 정도는 파라미터를 그대로 넘겨도 된다.

## 3. 도메인 모델링 - 15점

확인할 것:

- Entity가 단순 테이블 매핑 객체에 머물지 않는가
- Entity 생성 시 유효한 상태를 보장하는가
- 정적 팩토리 메서드나 의미 있는 생성 메서드를 사용하는가
- 도메인 규칙이 적절한 위치에 있는가
- Value Object로 분리할 만한 개념을 primitive/String/BigDecimal로 방치하지 않았는가

Entity에 들어가도 좋은 것:

- 자기 상태 검증
- 자기 상태 변경
- 자기 필드만 사용하는 계산
- 생성 시 불변 조건 보장
- 의미 있는 정적 팩토리 메서드

Entity에 넣으면 안 좋은 것:

- Repository 호출
- 외부 API 호출
- DTO 변환
- 트랜잭션 흐름 조립
- 여러 Aggregate를 조회하거나 조합하는 로직

감점 예:

- Entity가 setter로 무제한 변경 가능하다
- Entity factory가 검증 없이 필드만 채운다
- 모든 도메인 규칙이 Service에 몰려 있다
- 도메인 개념이 이름 없는 private method로만 숨어 있다

## 4. 유스케이스 흐름 설계 - 12점

확인할 것:

- Application Service 또는 Facade 메서드를 읽으면 업무 흐름이 보이는가
- 세부 계산/정책 로직은 별도 객체로 분리되어 있는가
- private method와 별도 클래스 분리 기준이 적절한가
- 조회, 계산, 저장, 응답 변환의 책임이 구분되어 있는가
- 트랜잭션 경계가 유스케이스 단위로 잡혀 있는가

별도 클래스로 분리할 후보:

- 독립적인 비즈니스 규칙이다
- DB 없이 단위 테스트하고 싶다
- 변경 이유가 현재 클래스와 다르다
- 다른 유스케이스에서도 재사용될 수 있다
- 도메인 이름을 붙일 수 있다

private method로 충분한 경우:

- 해당 클래스 내부 흐름을 읽기 좋게 만드는 보조 로직이다
- 밖에서 의미가 거의 없다
- 따로 테스트할 가치가 낮다
- 현재 클래스와 변경 이유가 같다

감점 예:

- 하나의 Service 메서드가 검증, 조회, 계산, 저장, 응답 변환을 모두 수행한다
- 독립 테스트 가능한 계산 로직이 private method에 묻혀 있다
- 조회와 계산이 한 메서드에 결합되어 있다

## 5. 예외 처리 - 10점

확인할 것:

- 공통 예외 처리 구조가 있는가
- 도메인별 ErrorCode 또는 BaseErrorCode 구조가 적절한가
- 핵심 비즈니스 실패가 구체 예외로 표현되는가
- HTTP status와 비즈니스 error code가 분리되어 있는가
- 예외 메시지와 응답 메시지 정책이 일관적인가

추천 구조:

```text
common/error
├── BaseErrorCode
├── CommonErrorCode
├── BusinessException
└── GlobalExceptionHandler

domain/exception
├── DomainErrorCode
└── SpecificBusinessException
```

감점 예:

- 모든 예외를 범용 커스텀 예외 하나로만 처리한다
- ErrorCode가 한 파일에 무분별하게 커진다
- 예외에 상세 메시지를 넣고 응답에서는 버린다
- 400으로 처리할 비즈니스 오류를 500으로 처리한다
- 구체 예외 이름만 봐서는 어떤 도메인 실패인지 알기 어렵다

## 6. 테스트 가능성 및 테스트 품질 - 15점

확인할 것:

- 핵심 도메인 규칙이 단위 테스트 가능한 구조인가
- 외부 API, DB 없이 테스트 가능한 계산/정책 객체가 있는가
- Controller 통합 테스트와 Domain/Application 단위 테스트가 구분되어 있는가
- 실패 케이스 테스트가 충분한가
- 테스트 이름이 요구사항을 설명하는가

좋은 테스트 대상:

- Entity factory와 상태 변경 메서드
- Value Object
- Domain Service 또는 Converter
- Application Service의 유스케이스 흐름
- Controller의 요청/응답 계약
- 예외 응답

감점 예:

- 테스트가 거의 없다
- Controller 테스트만 있고 핵심 계산 단위 테스트가 없다
- 성공 케이스만 테스트한다
- Mock이 과도해서 실제 로직 검증이 약하다
- 테스트 이름이 구현 세부사항만 설명한다

## 7. 트랜잭션과 영속성 설계 - 8점

확인할 것:

- 쓰기 유스케이스에 `@Transactional`이 적절히 적용되어 있는가
- 조회 유스케이스에 `@Transactional(readOnly = true)`가 적용되어 있는가
- 트랜잭션 경계가 Repository가 아니라 Application Service 또는 Facade에 있는가
- Entity와 Repository 사용이 계층 경계를 침범하지 않는가
- 명백한 N+1 또는 반복 쿼리 문제가 없는가

감점 예:

- 트랜잭션 경계가 불명확하다
- Controller가 Entity를 직접 다룬다
- 조회 DTO 변환이 트랜잭션 밖에서 Lazy Loading에 의존한다
- Repository 메서드에 비즈니스 흐름이 묻어 있다

## 8. 네이밍과 패키지 구조 - 8점

확인할 것:

- 클래스 이름이 역할을 정확히 설명하는가
- `ServiceImpl`, `Manager`, `Processor`, `Util` 같은 모호한 이름을 남발하지 않는가
- `Facade`라는 이름이 실제 조합 역할에 맞게 쓰였는가
- 구현체가 하나뿐인데 관성적으로 interface를 만들지 않았는가
- 패키지가 도메인과 계층 경계를 잘 드러내는가

권장 네이밍 예:

- `PostApplicationService`
- `PostCommandService`
- `PostQueryService`
- `CreatePostCommand`
- `PostInfo`
- `PostResponse`
- `SlugGenerator`
- `PostPolicy`

주의할 네이밍:

- `PostServiceImpl`
- `CommonUtil`
- `BusinessManager`
- `DataProcessor`
- 역할이 없는 `Facade`

감점 예:

- 모든 서비스가 `Service`/`ServiceImpl` 구조다
- 파일명만 봐서는 책임을 알 수 없다
- 모든 DTO를 한 클래스 내부에 몰아넣었다
- `Facade`가 실제로는 단순 Repository 조회 wrapper다

## 9. 과한 설계 여부 - 5점

확인할 것:

- 프로젝트 규모에 비해 불필요하게 추상화하지 않았는가
- 단순 조회까지 과도하게 Command/Query/Mapper/Facade로 쪼개지 않았는가
- 현재 요구사항 기준으로 납득 가능한 분리인가
- 패턴 적용이 가독성을 실제로 높였는가

감점 예:

- 구현체가 하나인데 무의미한 인터페이스를 남발한다
- 필드만 그대로 전달하는 클래스가 지나치게 많다
- 단순 CRUD에 과도한 DDD 구조를 적용했다
- 설계 패턴을 적용하기 위해 오히려 코드 흐름이 안 보인다

## 최종 리뷰 출력 형식

리뷰어는 아래 형식으로 답변한다.

```markdown
## 총점

XX / 100

## 한줄 평가

실무 기준으로 현재 코드의 수준을 냉정하게 요약.

## 가장 큰 문제 5개

1. 문제 제목
   - 위치:
   - 왜 문제인지:
   - 어떻게 고치면 좋은지:

## 잘한 점 3개

실제 장점만 적고 과장하지 않는다.

## 우선순위별 개선 작업

### P1: 반드시 고칠 것

### P2: 고치면 구조가 좋아지는 것

### P3: 여유 있을 때 개선할 것

## 리팩토링 방향 제안

- 패키지 구조:
- 주요 클래스 이름:
- DTO 흐름:
- 테스트 전략:

## 면접/과제 평가 관점에서 감점될 부분

## 다음 리팩토링 단계에서 집중할 학습 포인트
```

## 현재 학습 기준 요약

리팩토링할 때 아래 기준을 계속 적용한다.

- Controller는 얇게 유지한다.
- 상태 변경은 `Request -> Command -> ApplicationService -> Result/Info -> Response` 흐름을 우선 고려한다.
- 복잡한 조회 조건은 `Request -> Query/Condition`으로 분리한다.
- 단순 조회는 억지로 Query 객체를 만들지 않는다.
- Entity는 자기 상태와 도메인 규칙만 다룬다.
- Entity는 DTO를 알면 안 된다.
- DTO의 `toEntity()`보다 Entity의 `of()` 또는 전용 Factory를 우선 고려한다.
- 독립적인 계산/정책 로직은 private method보다 별도 객체로 분리한다.
- DB 없이 테스트하고 싶은 로직은 분리 후보로 본다.
- 구현체가 하나뿐인 `Service`에 interface와 `Impl`을 관성적으로 만들지 않는다.
- `Facade`는 여러 컴포넌트를 조합할 때만 사용한다.
- 에러 코드는 도메인별로 소유권을 나누는 방향을 고려한다.
- 조회에는 `@Transactional(readOnly = true)`, 쓰기에는 `@Transactional`을 명확히 둔다.
- 테스트는 Controller보다 Domain/Application 로직을 먼저 촘촘히 검증한다.
