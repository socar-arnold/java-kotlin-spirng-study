# Stage 3 · Phase 4-C ② — 영속성 컨텍스트, Dirty Checking, @Transactional

> 날짜: 2026-06-09 (Phase 4-C 졸업)
> 의미: JPA의 진짜 차별점 — TypeORM/Prisma에는 없는 마법 손으로 확인.

---

## ① 영속성 컨텍스트(Persistence Context)
- **트랜잭션 안에서 "조회된 엔티티들"을 들고 있는 1차 캐시 + 스냅샷 저장소.**
- 트랜잭션 시작 = OPEN, 종료(commit) = CLOSE.
- 그 안에서 같은 엔티티는 한 번만 DB 조회 (이후엔 캐시 hit).

## ② Dirty Checking (변경 감지)
- 영속성 컨텍스트가 *조회 시점의 원본 스냅샷* 보관 → commit 직전 *현재 상태와 비교* → 바뀐 필드 발견 시 **자동 UPDATE 발행**.
- 결과: `repo.save()` **호출 없이** 필드만 바꿔도 DB 반영.

```kotlin
@Transactional
fun updateName(id: Long, newName: String): User {
    val user = findById(id)        // SELECT 1번 (영속성 컨텍스트에 적재)
    user.name = newName            // 메모리 변경
    return user                    // 트랜잭션 종료 시 UPDATE 자동 발행 ⭐
}
```

### 실측 SQL 로그 (직접 본 것)
```
[exec-3] select ... from users where id=?           ← findById
[exec-3] binding (1:BIGINT) <- [1]
[exec-3] update users set email=?,name=? where id=?  ← ⭐ 자동! repo.save() 안 부름
[exec-3] binding (1:VARCHAR) <- [arnold@example.com]
[exec-3] binding (2:VARCHAR) <- [Arnold Updated]
[exec-3] binding (3:BIGINT) <- [1]
```
같은 스레드 = **하나의 트랜잭션** = 영속성 컨텍스트 활성 구간.

## ③ `@Transactional`
- 메서드 시작 = BEGIN, 정상 종료 = COMMIT, 예외 = ROLLBACK.
- `readOnly = true` = 조회 전용. dirty checking·flush 스킵 (약간 최적화).
- 영속성 컨텍스트는 **이 범위 안에서만 살아있음** → dirty checking은 트랜잭션 끝날 때 발동.

## ④ Service 계층 분리 (Controller → Service → Repository)
```kotlin
@Service
class UserService(private val repo: UserRepository) {
    @Transactional(readOnly = true) fun findAll(): List<User> = repo.findAll()
    @Transactional(readOnly = true) fun findById(id: Long): User = 
        repo.findById(id).orElseThrow { NoSuchElementException("user $id not found") }
    @Transactional fun create(name, email): User = repo.save(User(name=name, email=email))
    @Transactional fun updateName(id, newName): User { val u = findById(id); u.name = newName; return u }
    @Transactional fun delete(id): Unit { repo.delete(findById(id)) }
}
```
- **Controller**: HTTP 입출력만
- **Service**: 비즈니스 로직 + 트랜잭션 경계
- **Repository**: DB 접근만

## ⑤ Kotlin 함정 — `val` → `var`
JPA가 필드를 *바꿔야* dirty checking 됨. 그래서 엔티티 필드는 보통 `var`:
```kotlin
@Entity
class User(
    @Id @GeneratedValue(...) val id: Long = 0,    // PK는 val 유지
    var name: String,                               // var (dirty checking 대상)
    var email: String,
)
```
Kotlin 불변 철학과 살짝 안 맞지만 JPA 관용. (불변 유지하려면 record-style entity 등 다른 방법 있으나 복잡)

## ⑥ `@RestControllerAdvice` — 전역 예외 처리 미리보기 (Phase 4-D)
```kotlin
@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e): ResponseEntity<*> =
        ResponseEntity.status(404).body(mapOf("error" to e.message))
}
```
→ Service가 throw하면 컨트롤러 코드 안 건드리고 **전역적으로** 잡아 404로 변환. 모든 컨트롤러 적용.

## ⑦ 시니어 시선 — 미세 관찰
### (a) UPDATE가 전 컬럼
Hibernate 기본은 모든 컬럼 업데이트 → `@DynamicUpdate`로 *바뀐 것만* 업데이트 가능. (Phase 4-G)

### (b) `spring.jpa.open-in-view is enabled by default` 경고
**OSIV(Open Session In View)**: 영속성 컨텍스트를 View 렌더링까지 살려둠 → DB 커넥션 응답 끝까지 점유 → 트래픽 많으면 풀 고갈. 실무 대형 프로젝트는 보통 `spring.jpa.open-in-view: false`. (Phase 4-G에서 깊이)

---

## 핵심 한 줄 요약
- **영속성 컨텍스트 = 트랜잭션 안의 1차 캐시 + 원본 스냅샷.**
- **Dirty Checking = save() 없이도 자동 UPDATE.** TypeORM/Prisma와 결정적 차이.
- **`@Transactional` Service 계층**이 dirty checking·트랜잭션 경계의 기본 형태.
- 함정: 엔티티 `var`, OSIV는 운영에서 끄는 게 정석.

## 다음 — Stage 3 절반
- Phase 4-D 예외처리·Validation(@Valid + Bean Validation, 전역 ExceptionHandler 본격)
- Phase 4-E AOP·Filter·Interceptor
- Phase 4-F Spring Security·JWT
- Phase 4-G JPA 심화(연관관계·페치 전략·N+1·@DynamicUpdate·OSIV 끄기)
- Phase 4-H 테스트 전략 (@SpringBootTest, @WebMvcTest, @DataJpaTest, MockMvc)
- Phase 4-I 설정 관리·Flyway
- 미션: 블랙잭, 로또
