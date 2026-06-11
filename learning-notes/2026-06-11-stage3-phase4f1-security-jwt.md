# Stage 3 · Phase 4-F ① — Spring Security + JWT 🔐

> 날짜: 2026-06-11
> 의미: 무상태 토큰 인증 흐름 손에 들어옴.
> 페르소나: TypeScript 개발자 (NestJS Passport JWT 매핑)

---

## ① WHY
API를 그대로 두면 누구나 호출 가능. **인증(Authentication)** 으로 *누구냐*, **인가(Authorization)** 로 *뭐 할 수 있냐* 검증. REST는 무상태(Stateless)라 *세션* 대신 **JWT 토큰**이 자연스러움 — 클라가 토큰을 *들고 다님*.

## ② JWT 구조 (JWS, 서명)
```
header.payload.signature
```
- **header**: 알고리즘(HS256 등)
- **payload**: 클레임 — `sub`(식별자), `exp`(만료), `roles`
- **signature**: 서버 비밀키로 서명

### 🎯 시니어 포인트: 암호화 아니라 서명
- **JWS(서명)** = payload *누구나 읽음*. 단 *진위/변조* 검증.
- **JWE(암호화)** = payload 못 읽음. 거의 안 씀.
- 일반 "JWT" = JWS.
- → **민감 정보(비밀번호·카드 등) payload 절대 금지.** [jwt.io](https://jwt.io)에 토큰 붙이면 payload 그대로 보임.

### 변조가 막히는 이유
서버는 받은 토큰의 `payload + 비밀키`로 서명을 *다시 계산* → 토큰 안의 signature와 비교. 비밀키 모르면 *유효한 서명 생성 불가* → 변조된 토큰 reject.

## ③ Spring Security Filter Chain
Spring Security는 ~15개 Filter 줄지어 등록. 우리 JwtAuthFilter는 그 중간에 끼움.

```
요청 → [SecurityContextHolderFilter] → [CsrfFilter] → ... 
    → ⭐JwtAuthFilter → [UsernamePasswordAuthFilter] → ...
    → DispatcherServlet → @RestController
```

→ Phase 4-E의 Filter/Interceptor/AOP 비교에서 *Filter*. 컨트롤러 도달 전 차단.

## ④ 인증 흐름
```
1. POST /auth/login (email+password)
       ↓
2. 서버: 검증 → JWT 발급 → 응답 본문
       ↓
3. 이후 모든 요청 헤더: Authorization: Bearer eyJ...
       ↓
4. JwtAuthFilter: 헤더에서 토큰 추출 → 서명 검증 → SecurityContext에 인증 저장
       ↓
5. 컨트롤러: 이미 인증된 요청만 받음
   - 토큰 없음/실패 → 차단
```

## ⑤ HOW — 구현 요약
### 의존성
```kotlin
implementation("org.springframework.boot:spring-boot-starter-security")
implementation("io.jsonwebtoken:jjwt-api:0.12.6")
runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
```

### JwtUtil — 토큰 생성·검증 (HS256, secret ≥ 32 bytes)
### JwtAuthFilter — `OncePerRequestFilter` 상속, doFilterInternal에서 헤더 파싱·검증·SecurityContext 세팅
### SecurityConfig — `@EnableWebSecurity` + `SecurityFilterChain` 빈
```kotlin
.csrf { it.disable() }                               // REST API니까
.sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
.authorizeHttpRequests {
    it
        .requestMatchers(HttpMethod.POST, "/users").permitAll()     // 회원가입 공개
        .requestMatchers("/auth/**", "/hello/**", "/visit", "/stats").permitAll()
        .anyRequest().authenticated()
}
.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
```
### AuthController — /auth/login (임시: 이메일만 보고 토큰 발급. 비밀번호는 ②)

## ⑥ 실측
```
POST /users (회원가입)                    → 201 ✅
POST /auth/login                          → 200 + {"token":"eyJ..."}
GET  /users  (토큰 없음)                  → 403 ❌
GET  /users  (Authorization: Bearer ...)  → 200 ✅
```

## ⑦ 함정 통과
### a) 닭과 달걀 (chicken-and-egg)
처음엔 `/users` 전체를 보호했더니 회원가입조차 못함. **HTTP 메서드별 정책**(`POST /users`만 permitAll)으로 해결. 실무 패턴.

### b) Spring Security 6 → 401 대신 403 기본
- 401 = 인증 안 됨 + *해결법 안내*(WWW-Authenticate)
- 403 = 권한 없음(해결법 X)
- Security 6은 인증 없을 때 403 기본 (RFC 엄밀성). 우리 케이스는 사실 401이 의미상 맞음 → `AuthenticationEntryPoint` 커스텀으로 변경 가능.

### c) bootRun 재시작 필수 (Security 설정 변경 시)
런타임 핫스왑 안 됨. *변경 → 재시작 → curl `-i`로 상태 확인* 세트.

### d) `-i` 없이 curl 보지 마라
빈 응답에 속음. 시니어 디버깅 1순위 습관.

## ⑧ Phase 4-E 연결
LoggingAspect가 *실패 케이스도* 잡았음:
```
➡️ [START] AuthController.login(..)
❌  [FAIL]  AuthController.login(..)  ex=NoSuchElementException
```
AOP가 정상/예외 모두 가로채는 try-catch 패턴. 실무 APM 모니터링과 동일.

## ⑨ TS/NestJS 매핑
- `@Injectable() class JwtStrategy` (Passport) ≈ `JwtAuthFilter`
- `AuthGuard('jwt')` + `@UseGuards()` ≈ Spring `authorizeHttpRequests`
- 같은 사고, 다른 셋업.

## 핵심 한 줄 요약
- **JWT = 서명된 JSON.** 누구나 읽지만 변조는 막힘.
- **인증은 Filter에서** (컨트롤러 도달 전 차단). Spring Security가 Filter Chain 제공.
- **무상태 + STATELESS + CSRF disable** = JWT REST 표준 설정.
- 디버깅: **메서드별 정책 / `-i` 필수 / Security 변경 후 재시작**.

## 다음 — Phase 4-F ②
- **BCrypt** — 비밀번호 해싱 (느린 해시 + 솔트 + 비용 파라미터). 평문/MD5 절대 금지.
- 회원가입 시 password 받아 BCrypt 해싱 후 저장.
- 로그인 시 입력 vs DB 해시 비교.
- **`@PreAuthorize`** — 메서드 레벨 권한 (또 AOP!).
- **401 vs 403** 깔끔히 분리 (AuthenticationEntryPoint).

---

## 📝 미니 퀴즈 회상 (적극적 회상)

### Q1 — JWT 세 부분
**답:** header(알고리즘) / payload(식별자·만료·권한) / signature(서명값). ✅ 완벽.

### Q2 — 암호화 vs 서명
**답:** 서명. + "질문이 애매하다" 직감.
**리뷰:** ✅ 정답. 정확히는 **JWS(서명) vs JWE(암호화)** 둘 다 JWT 표준이라 헷갈리는 게 정상. 우리가 쓴 거 = JWS. payload는 누구나 읽음 → 민감 정보 금지.

### Q3 — 토큰 변조
**답:** 서명값이 달라서 안 됨.
**리뷰:** ✅ 시니어 답변. 정리: 서버가 받은 token으로 서명 재계산 → 비교 → 불일치 → reject. 비밀키 모르면 유효한 서명 생성 불가.

### Q4 — 401 vs 403
**답:** 401은 인증 안 됨 + 해결법 안내. 403은 권한 없음만.
**리뷰:** ✅ 완벽. + Spring Security 6 기본은 403(RFC 엄밀성). 401 받고 싶으면 AuthenticationEntryPoint 커스텀.

### Q5 — 비밀번호 저장 방식 (a/b/c)
**답:** (c) BCrypt. 이유: "역으로 비교할 수 있게."
**리뷰:** ✅ 답 정확. 이유 보강 — 비교 가능은 (b)도 만족. *진짜 이유*: BCrypt는 **느리게 설계** + **솔트 자동** + **비용 파라미터** → 무차별 대입·레인보우 테이블 방어. **"느림이 곧 보안"** (정상 사용자 100ms 못 느낌, 공격자 수억 번 × 100ms = 115일). MD5/SHA-256은 *데이터 무결성* 용도지 *비밀번호* 용도가 아님.

### 종합 평가
| 문항 | 결과 |
|---|---|
| Q1 | ✅ 완벽 |
| Q2 | ✅ 정답 + JWS/JWE 구분 새로 인지 |
| Q3 | ✅ 시니어 답변 |
| Q4 | ✅ 완벽 |
| Q5 | ✅ 답 정확, *왜 BCrypt만인가* 새로 인지 |

**오늘 박힌 한 줄:** *"JWT는 서명(JWS)이지 암호화 아님. 변조는 서명 재계산으로 막음. 비밀번호 해싱은 *느리게 설계된* BCrypt/Argon2가 정답 — 빠른 해시(MD5/SHA)는 무차별 대입에 무너짐."*
