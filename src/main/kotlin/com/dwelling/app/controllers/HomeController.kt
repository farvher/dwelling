package com.dwelling.app.controllers

import com.dwelling.app.domain.City
import com.dwelling.app.domain.Visitor
import com.dwelling.app.repository.VisitorRepository
import com.dwelling.app.services.SearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody

import org.springframework.web.reactive.function.server.body
import org.springframework.web.servlet.function.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.time.Duration
import javax.sql.DataSource
import java.time.Duration.ofMillis




@Controller
class HomeController {

    @Autowired
    lateinit var visitorRepository: VisitorRepository

    @Autowired
    lateinit var dataSource: DataSource

    @Autowired
    lateinit var searchService: SearchService

//    @ResponseBody
//    @GetMapping(path = ["/", ""])
    fun getIndex(): Flux<City> {

        val schema = dataSource.connection.schema

        var cities = searchService.searchByQueryString("BOGOTA")

        searchService.createCity(City(1, "BOGOTA", "Colombia"))
        val fluxList = searchService.searchByQueryString("BOGOTA")


        return fluxList


    }

    @GetMapping(path = ["/test1"])
    @ResponseBody
    fun test1( ) : String? {
       return  get(5000).block()
    }

    @GetMapping(path = ["/test2"])
    @ResponseBody
    fun test2() : Mono<String>{
        return  get(5000)
    }


//    @GetMapping("/{delayMillis}")
    operator fun get(@PathVariable delayMillis: Long): Mono<String> {
        return Mono.just("OK")
                .delayElement(Duration.ofMillis(delayMillis))
    }




}
