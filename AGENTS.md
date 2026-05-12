# devog-be 작업 원칙

- 사용자는 기존 코드를 조금씩 DDD 스타일로 개선하는 중이다.
- 칭찬보다 냉정한 설계 평가와 개선점을 우선한다.
- Controller는 얇게 유지한다.
- 상태 변경 유스케이스는 Request -> Command -> ApplicationService -> Result/Info -> Response 흐름을 우선 고려한다.
- 복잡한 조회 조건은 Query/Condition으로 분리하되, 단순 조회는 과하게 감싸지 않는다.
- Entity는 자기 상태와 도메인 규칙만 다룬다.
- Entity는 DTO를 알면 안 된다.
- DTO의 toEntity()보다 Entity의 of() 또는 전용 Factory를 우선 고려한다.
- 독립적인 계산/정책 로직은 별도 객체로 분리하고 단위 테스트 가능하게 만든다.
- 구현체가 하나뿐인 Service에 interface + Impl을 관성적으로 만들지 않는다.
- Facade는 여러 컴포넌트를 조합할 때만 사용한다.
- 조회에는 @Transactional(readOnly = true), 쓰기에는 @Transactional을 명확히 둔다.
- 테스트는 Controller보다 Domain/Application 로직을 먼저 촘촘히 검증한다.
