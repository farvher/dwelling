package com.dwelling.app.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class HomeController {

    @GetMapping
    fun home(): Mono<String> = Mono.just("Dwelling Api")
}