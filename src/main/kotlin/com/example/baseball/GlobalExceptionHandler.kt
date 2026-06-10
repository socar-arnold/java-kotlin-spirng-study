package com.example.baseball

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

data class ErrorResponse(
    val status: Int,
    val error : String,
    val message: String,
    val fieldError: List<ValidationError> = emptyList()
)

data class ValidationError(
    val field: String,
    val message: String?,
)

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<ErrorResponse>  = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        ErrorResponse(404, "NOT_FOUND", e.message ?: "리소스를 찾을 수 없습니다")
    )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val fieldErrors = e.bindingResult.fieldErrors.map{
            ValidationError(field = it.field, message = it.defaultMessage)
        }

        return ResponseEntity.badRequest().body(
            ErrorResponse(400, "VALIDATION_FAILED", "입력값이 올바르지 않습니다", fieldErrors)
        )
    }
}