# Stage 2 · 미션 ② RSS리더 📰 (Stage 2 마지막 미션)

> 날짜: 2026-06-05
> 의미: 코루틴 + supervisorScope + 의존성 주입(DI) 합쳐서 *실제로 쓸 만한* 패턴 완성.
> 페르소나: TypeScript 개발자

---

## 변형 결정 — 학습 본질에 집중
실제 HTTP/XML 대신 **`FeedClient` 인터페이스 추상화 + 페이크 클라이언트**. 이유:
- 진짜 HTTP는 네트워크 변수(404·타임아웃) → TDD 비결정적.
- 학습 본질은 "**병렬 fetch + 일부 실패 격리**" 의 코루틴 패턴.
- 진짜 HTTP는 Phase 4 Spring에서 만남(WebClient/Ktor 등).

## 설계 (DI + 코루틴 핵심 패턴)
```kotlin
interface FeedClient { suspend fun fetch(url: String): String }   // 추상화 ← DI

data class Feed(val source: String, val items: List<String>)

fun parseFeed(source: String, xml: String): Feed {
    val titles = Regex("<title>(.*?)</title>", RegexOption.DOT_MATCHES_ALL)
        .findAll(xml).map { it.groupValues[1] }.toList()
    return Feed(source, titles.drop(1))                            // 첫 title은 채널명
}

class RssReader(private val client: FeedClient) {
    suspend fun readAll(urls: List<String>): List<Feed> = supervisorScope {
        urls.map { url ->
            async { parseFeed(url, client.fetch(url)) }            // 각자 try-catch 없이 throw 허용
        }.mapNotNull { deferred ->
            try { deferred.await() }                                // await 시 catch
            catch (e: Exception) { null }                           //   실패한 건 null로
        }
    }
}
```

## 핵심 패턴 둘
1. **DI(Dependency Injection) 입문**: `RssReader`는 *어떻게 fetch*하는지 모름. 테스트엔 페이크, 실무엔 진짜 HTTP. → Phase 4 Spring DI의 전조.
2. **supervisorScope + try/catch on await**: 어제 본 격리의 *실전 응용*. 한 피드 throw → 형제 안 죽고 → 다른 피드들 정상 반환.

> coroutineScope였다면 한 피드 throw 시 *전체 취소* → 살아남는 피드 0개. supervisorScope여야 부분 실패 OK.

## 실측 결과 (3 tests PASSED)
- **3개 피드 fetch: 315ms** (각 300ms × 3) → 직렬이면 900ms. **거의 정확히 가장 느린 하나**.
- **failingUrls={"b"}일 때:** `feeds.map{it.source}`가 `["a","c"]` — b는 빠지지만 다른 둘 살아남음 ✅.
- **여러 피드 병렬 + 모든 아이템 수집** ✅.

## Q&A — 왜 try/catch를 async **안**에 안 넣었나?
- async **안에** try/catch 두면: async가 throw 안 함 → coroutineScope에서도 동작. supervisorScope의 격리 효과가 *실제로 발휘되지 않음*(증명 못함).
- await **바깥**에 try/catch 두면: async가 진짜 throw → supervisorScope가 형제 보호 → await에서만 받음. **supervisorScope의 가치가 명확히 드러남**.
- 실무 패턴으로도 후자가 정석(에러 처리 위치가 한 곳).

## 디버깅 교훈 — `settings.gradle.kts` 오염
파일 생성 도중 `include("src:test:RssReaderTest.kt")` 가 settings에 자동 삽입돼 Gradle이 파일을 *서브 프로젝트*로 인식 → 빌드 실패. IntelliJ "New → Gradle Module" 같은 메뉴를 잘못 골랐을 가능성. 해결: 그 줄 삭제 + 진짜 파일은 **New → Kotlin Class/File → File** 로 만들기.

## 핵심 한 줄 요약
- **supervisorScope { 각자 async } + await 바깥 try/catch + mapNotNull** = 부분 실패 OK 병렬 fetch 표준 패턴.
- `FeedClient` 인터페이스 추상화 = DI 입문 + 테스트 가능성 확보.
- 측정: 직렬 900ms → 병렬 315ms — async/await의 가치 증명.

## 다음
**🏆 Stage 2 졸업 → Stage 3 (Spring·JPA·테스트)** 진입. 진짜 백엔드.
