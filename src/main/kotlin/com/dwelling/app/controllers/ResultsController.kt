package com.dwelling.app.controllers

import com.dwelling.app.domain.Property
import com.dwelling.app.dto.FilterDto
import com.dwelling.app.elasticsearch.IDwellingsSeach
import com.dwelling.app.repository.PropertyRepository
import com.dwelling.app.security.controller.UserRestController
import com.dwelling.app.services.SearchService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono


@RestController
class ResultsController {

    val logger: Logger = LoggerFactory.getLogger(ResultsController::class.java)
    @Autowired
    private lateinit var searchService: SearchService<Property>
    @Autowired
    private lateinit var dwellingsSearch: IDwellingsSeach
    @Autowired
    private lateinit var userRestController: UserRestController
    @Autowired
    private lateinit var propertyRepository: PropertyRepository


    @PostMapping("/search")
    fun searchByFilter(@RequestBody filters: List<FilterDto>): Mono<ResponseEntity<List<Property>>> {
        logger.info("[searchByFilter] ${filters.size} filters ")
        filters.forEach { logger.info("${it.filterKey} => ${it.filterValue} - ${it.filterRange} | ${it.filterType}") }
        val results = dwellingsSearch.findByFilters(filters)
        logger.info("${results.size} properties found")
        return ResponseEntity.ok().body(results).toMono()
    }


    @PostMapping("/search/{keyword}")
    fun searchByKeyword(@RequestBody filters: Map<String, Any>, @PathVariable keyword: String): Mono<ResponseEntity<List<Property>>> {
        TODO("debe reconocer las palabras en keywords y aplicar filtros")
    }

}
