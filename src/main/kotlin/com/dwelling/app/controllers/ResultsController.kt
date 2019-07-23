package com.dwelling.app.controllers

import com.dwelling.app.domain.Property
import com.dwelling.app.services.SearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono


@RestController
class ResultsController {


    @Autowired
    private lateinit var searchService: SearchService<Property>

    @PostMapping("/search")
    fun searchByFilter(@RequestBody filters : Map<String,Object>): Mono<ResponseEntity<List<Property>>>{

        val results = searchService.searchByQueryString("lorem")

        return ResponseEntity.ok().body(results).toMono()
    }

    @PostMapping("/search/{keyword}")
    fun searchByKeyword(@RequestBody filters : Map<String,Object>,@PathVariable keyword: String): Mono<ResponseEntity<List<Property>>>{
        val results = searchService.searchByQueryString(keyword)
        return ResponseEntity.ok().body(results).toMono()
    }

}
