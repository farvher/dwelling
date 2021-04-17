package com.dwelling.app.elasticsearch

import com.dwelling.app.dto.FilterDto
import com.dwelling.app.dto.PropertyDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface DwellingSearch {

    fun findByFilters(filters: List<FilterDto>): Flux<PropertyDto>

    fun findByKeyword(keyword: String): Flux<PropertyDto>

    fun findByFiltersAndKeyword(filters: List<FilterDto>, keyword: String): Flux<PropertyDto>

    fun findById(id: Long) : Mono<PropertyDto>

}
