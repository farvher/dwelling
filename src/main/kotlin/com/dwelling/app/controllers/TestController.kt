package com.dwelling.app.controllers

import com.dwelling.app.domain.*
import com.dwelling.app.dto.EFilter
import com.dwelling.app.dto.FilterDto
import com.dwelling.app.dto.FilterType
import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.elasticsearch.IDwellingsSearch
import com.dwelling.app.repository.*
import com.dwelling.app.repository.UserRepository
import com.dwelling.app.services.SearchService
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate
import javax.sql.DataSource

@RestController()
class TestController {

    @Autowired
    private lateinit var dwellingsSearch: IDwellingsSearch


    @Autowired
    private lateinit var visitorRepository: VisitorRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var elasticPropertyRepository: ElasticPropertyRepository


    @GetMapping(value = ["/test/search"])
    fun getElastic() = dwellingsSearch.findByKeyword("Lorem")

    @GetMapping("/test/search/{word}")
    fun getElastic2(@PathVariable word: String) = dwellingsSearch.findByKeyword("Lorem")

    @GetMapping("/test/filter")
    fun getSampleFilter(): FilterDto {
        return FilterDto(EFilter.BATHROOMS, 2, filterRange = listOf(1, 3), filterType = FilterType.RANGE)
    }

    @GetMapping("/test/filters")
    fun getSampleFilters(): List<FilterDto> {
        return listOf(FilterDto(EFilter.BATHROOMS, 2, filterRange = listOf(1, 3), filterType = FilterType.RANGE))
    }

    @GetMapping("/test/visitors")
    fun getVisitors(): Flux<Visitor> = visitorRepository.findAll()

    @GetMapping("/test/users")
    fun getUsers(): Flux<User> = userRepository.findAll()


}
