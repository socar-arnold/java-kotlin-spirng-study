plugins {
    kotlin("jvm") version "2.3.20"
    kotlin("plugin.spring") version "2.3.20"             // ← Spring용 (open 자동)
    kotlin("plugin.jpa") version "2.3.20"              // ⭐ JPA용 (no-arg ctor 자동 생성)

    id("org.springframework.boot") version "3.3.4"        // ← Spring Boot
    id("io.spring.dependency-management") version "1.1.6"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

    // ⭐ Spring Boot Web + Kotlin Jackson
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:5.8.1")
    testImplementation("io.kotest:kotest-assertions-core:5.8.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.springframework.boot:spring-boot-starter-validation")  // ⭐ 추가
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")   // ⭐ JPA
    implementation("org.springframework.boot:spring-boot-starter-aop")
    //    jwt related
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
    //    jwt related
    runtimeOnly("com.h2database:h2")                                          // ⭐ 인메모리 DB
}

tasks.test {
    useJUnitPlatform()
    testLogging { showStandardStreams = true; events("passed","failed","skipped") }
}

kotlin { jvmToolchain(21) }

// ⭐ 우리 레포엔 main이 여러 개라(Baseball, CarRacing 등) bootJar에 명시적으로 알려줌
springBoot { mainClass.set("com.example.baseball.HelloApplicationKt") }