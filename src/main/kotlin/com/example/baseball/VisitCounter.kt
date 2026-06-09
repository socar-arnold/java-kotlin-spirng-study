package com.example.baseball

import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger

@Service
class VisitCounter {
    private val count = AtomicInteger(0)
    fun increment():Int = count.incrementAndGet()
    fun current():Int = count.get()
}