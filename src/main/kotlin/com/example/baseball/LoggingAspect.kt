package com.example.baseball

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.hibernate.tool.schema.spi.ExceptionHandler
import org.springframework.stereotype.Component

@Aspect // 이 클래스에 Aspect들어있음
@Component // 빈으로 등록(스프링이 찾아내도록)
class LoggingAspect {
    //com.example.baseball에 관련된 모든 컨트롤러에 하위 logExecution등록
    @Around("execution(* com.example.baseball..*Controller.*(..))")
    fun logExecution(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature.toShortString() // "호출되는 UserController.create(..) 가져오기"
        val start = System.currentTimeMillis()

        println("➡️ [START] $signature args=${joinPoint.args.toList()}")

        return try{
            val result = joinPoint.proceed()
            val elapsed = System.currentTimeMillis() - start
            println("✅  [END]   $signature  took ${elapsed}ms")
            result
        }catch(e: Exception) {
            val elapsed = System.currentTimeMillis() - start
            println("❌  [FAIL]  $signature  took ${elapsed}ms  ex=${e.javaClass.simpleName}")
            throw e //흐름 유지를 위해 에러는 던짐
        }
    }
}