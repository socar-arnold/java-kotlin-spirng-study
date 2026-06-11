package com.example.baseball

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component //AOT위함
class JwtAuthFilter(private val jwtUtil: JwtUtil) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader(("Authorization"))?.removePrefix("Bearer")?.trim()

        if(!token.isNullOrEmpty()) {
            try{
                val email = jwtUtil.parseSubject(token)
                val auth = UsernamePasswordAuthenticationToken(email, null, emptyList())
                SecurityContextHolder.getContext().authentication = auth
            }catch(e: Exception){

            }
        }

        filterChain.doFilter(request, response)
    }


}