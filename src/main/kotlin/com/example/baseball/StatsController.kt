package com.example.baseball

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StatsController (private val counter: VisitCounter){
    @GetMapping("/visit")
    fun visit():Map<String, Int> = mapOf("visit" to counter.increment())

    @GetMapping("/stats")
    fun stats():Map<String, Int> = mapOf("totalVisits" to counter.current())
}