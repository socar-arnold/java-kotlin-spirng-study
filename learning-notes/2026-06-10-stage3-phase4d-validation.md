# Stage 3 · Phase 4-D — Validation & Global Exception Handling 🛡️

> 날짜: 2026-06-10
> 의미: 입력 검증의 시니어 패턴 정착. 전역 ErrorResponse 표준 모양.
> 페르소나: TypeScript 개발자 (class-validator/NestJS pipe 사고 활용)

---

## ① WHY
입력 검증을 컨트롤러에서 if-else로 처리하면 100개 메서드면 100번 반복. 잘못된 입력은 *입구*에서 막아야(Phase 1-F require 사고). Bean Validation = **어노테이션 한 줄로 검증 + 실패 시 표준 400 응답**.

## ② WHAT — Bean Validation 핵심 (TS class-validator 매핑)
| Java/Spring | TS class-validator | 의미 |
|---|---|---|
| `@NotBlank` | `@IsNotEmpty()` | null 거부 + 빈/공백 문자열 거부 |
| `@NotNull` | `@IsDefined()` | null만 거부 |
| `@NotEmpty` | — | null + 빈 거부. ⚠️ **공백 `"   "` 는 통과** |
| `@Email` | `@IsEmail()` | 이메일 형식 |
| `@Size(min=, max=)` | `@Length(...)` | 문자열 길이 |
| `@Min(N)`/`@Max(N)` | `@Min()`/`@Max()` | 숫자 범위 |
| `@Pattern(regexp=...)` | `@Matches(...)` | 정규식 |

### 엄격도 외우는 트릭
**Blank > Empty > Null** (이름이 더 강할수록 더 까다로움).

### Kotlin 함정 — `@field:`
data class ctor 프로퍼티엔 어노테이션 타깃을 명시:
```kotlin
data class CreateUserRequest(
    @field:NotBlank(message = "...")
    @field:Size(min = 2, max = 30)
    val name: String,
    @field:Email val email: String,
)
```
`@field:` 빼면 엉뚱한 곳(getter)에 붙어 검증 안 먹음.

## ③ HOW — 핸들러
```kotlin
data class ErrorResponse(
    val status: Int,
    val error: String,
    val message: String,
    val fieldErrors: List<ValidationError> = emptyList(),
)
data class ValidationError(val field: String, val message: String?)

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException) = ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ErrorResponse(404, "NOT_FOUND", e.message ?: "리소스 없음"))

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val fieldErrors = e.bindingResult.fieldErrors.map {
            ValidationError(it.field, it.defaultMessage)
        }
        return ResponseEntity.badRequest()
            .body(ErrorResponse(400, "VALIDATION_FAILED", "입력값이 올바르지 않습니다", fieldErrors))
    }
}
```

## ④ 실측 — 검증 실패 응답
`POST /users` with `{"name":"","email":"not-an-email"}` →
```json
HTTP/1.1 400
{
  "status": 400,
  "error": "VALIDATION_FAILED",
  "message": "입력값이 올바르지 않습니다",
  "fieldErrors": [
    {"field":"name","message":"이름은 2~30자"},
    {"field":"email","message":"이메일 형식이 아닙니다"},
    {"field":"name","message":"이름은 비어 있을 수 없습니다"}
  ]
}
```
**관찰:** `name` 에러가 *2개* 떴음 — 한 필드에 어노테이션이 여러 개면 *각각 다 검증·다 보고*. (단락 평가 안 함)

## ⑤ 시니어 디버깅 — 이름 충돌 함정
`com.example.baseball.FieldError` (우리) vs `org.springframework.validation.FieldError` (Spring의 bindingResult가 반환). 둘 다 같은 이름이라 IntelliJ 자동 import가 충돌. **해결 순위:**
1. 이름 자체를 *덜 충돌하게* (`ValidationError`로 변경) ← 우리 선택
2. `import ... as SpringFieldError` 별칭
3. 완전 한정명(FQN) `com.example.baseball.FieldError(...)`

## ⑥ 다음(4-E) 예고 — AOP는 우리 일상이었다
`@Transactional`(4-C) 자체가 **AOP**의 응용. Spring이 *프록시*로 우리 메서드 감싸서 BEGIN/COMMIT 자동 주입. 4-E에서 직접 작성하는 Aspect로 로깅·시간측정·인증 같은 횡단 관심사 처리.

---

## ⑦ 핵심 한 줄 요약
- 검증은 **`@Valid` 트리거 + Bean Validation 어노테이션 + 전역 ExceptionHandler** 조합으로 표준화.
- **Blank > Empty > Null** 엄격도. `@NotEmpty`는 공백 문자열 통과 함정.
- 여러 어노테이션 = 다 검증·다 보고 → 응답 단에서 정리 필요.
- `@RestControllerAdvice` + 표준 `ErrorResponse` = 프론트가 일관되게 처리 가능.

## ⑧ 다음
Phase 4-E AOP (`@Aspect`, `@Around`, Pointcut). `@Transactional`이 사실 AOP였다는 깨달음 + 로깅/시간측정 Aspect 직접 작성.

---

## 📝 Phase 종료 미니 퀴즈 (적극적 회상)

### Q1 회상 — Bean Validation 발동 시점
**답:** data class에 @NotBlank 등 붙이고 컨트롤러/서비스 인자에 `@Valid` 붙여야 트리거. 런타임에 발동(Reflection이 어노테이션 읽음). `@Valid` 빼면 검증 무시·그냥 통과.
**리뷰:** ✅ 정확. 보완: 서비스에 적용하려면 클래스 `@Validated` + 메서드 인자 `@Valid` 조합.

### Q2 회상 — @NotBlank/@NotNull/@NotEmpty 차이
**답:** Blank=공백X, Null=null만 거부, Empty=공백이면 안 됨.
**리뷰:** 🟡 Empty 함정. **`@NotEmpty`는 *비어 있으면* 안 됨이고, *공백 문자열 `"   "`은 통과시켜요.** 엄격도 **Blank > Empty > Null** 순. `@NotEmpty`는 컬렉션·배열에도 적용 가능.

### Q3 응용 — 비밀번호 검증
**답:** `@Size(min=8)` + `@Pattern(정규식)`. 정규식 자체는 못 떠올림.
**리뷰:** ✅ 도구 선택 완벽. 실무에서도 정규식은 검증된 거 가져다 씀(보안 직결). 예: `@Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d).+$")`.

### Q4 ⚠️함정 — name 필드 에러 2개
**답:** "@NotBlank만 뜨는 거 아님?"
**리뷰:** ⚠️ 둘 다 뜸. Bean Validation은 **여러 어노테이션 모두 검증, 위반한 거 모두 보고** (단락 평가 안 함). `""`은 NotBlank AND Size(min=2) 둘 다 위반. 프론트 UX 정리법: ① 필드별 첫 에러만 `groupBy` ② `@GroupSequence`로 순서·단락 ③ 단일 어노테이션으로 표현(Size만, NotBlank 빼기).

### Q5 🔭스트레치 — AOP 예고
**답:** "Log 패키지 있지 않으려나"
**리뷰:** 🔸 방향 좋음(프레임워크가 처리). AOP = **"메서드 호출 전/후/주변에 동작을 자동 주입"** 메커니즘. `@Around("execution(* ..Controller.*(..))")` 한 번 정의로 *모든* 컨트롤러 자동 적용. 비밀: Spring이 컨트롤러를 *프록시*로 감싸 빈 등록(Phase 2-D 동적 프록시 응용). `@Transactional`이 사실 AOP의 우리 일상 사례.

### 종합 평가
| 문항 | 결과 |
|---|---|
| Q1 | ✅ 정확 |
| Q2 | 🟡 NotEmpty 함정 보완 필요 |
| Q3 | ✅ 도구 선택 정확 |
| Q4 | ⚠️ 본질(다 검증·다 보고) 새로 인지 |
| Q5 | 🔸 방향 좋음, AOP+@Transactional 연결 새로 인지 |

**오늘 박힌 한 줄:** *"여러 어노테이션 = 다 검증·다 보고 / AOP = 횡단 관심사 자동 주입 / @Transactional이 사실 AOP의 우리 일상 사례."*
