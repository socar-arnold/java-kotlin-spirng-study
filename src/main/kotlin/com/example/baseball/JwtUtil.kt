package com.example.baseball

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtUtil (
    @Value("\${jwt.secret}") secret: String
) {
    private val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())
    private val expirationMs = 1000L * 60 * 60 * 24

    fun generate(email: String):String  =
        Jwts.builder()
            .subject(email)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expirationMs))
            .signWith(key)
            .compact()

    fun parseSubject(token:String):String = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload.subject
}