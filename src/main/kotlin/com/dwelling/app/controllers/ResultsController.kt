package com.dwelling.app.controllers

import com.dwelling.app.dto.FilterDto
import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.elasticsearch.IDwellingsSearch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux


@RestController
class ResultsController(
    private val dwellingsSearch: IDwellingsSearch
) {

    val logger: Logger = LoggerFactory.getLogger(ResultsController::class.java)

    @PostMapping("/search")
    fun searchByFilter(@RequestBody filters: List<FilterDto>): Flux<PropertyDto> {
        logger.info("[searchByFilter] ${filters.size} filters ")
        filters.forEach { logger.info("${it.filterKey} => ${it.filterValue} - ${it.filterRange} | ${it.filterType}") }
        return dwellingsSearch.findByFilters(filters)
    }


    @PostMapping("/search/{keyword}")
    fun searchByKeyword(@RequestBody filters: Map<String, Any>, @PathVariable keyword: String): Flux<PropertyDto> {
       return dwellingsSearch.findByKeyword("keyword")
    }

}
