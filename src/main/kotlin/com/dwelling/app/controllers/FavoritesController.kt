package com.dwelling.app.controllers

import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.services.PropertyService
import com.dwelling.app.services.VisitorService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import javax.servlet.http.HttpServletRequest

/**
 * RestController handle visitor properties favorites
 * @author fsanmiguel
 * */
@RestController("/favorites")
class FavoritesController {

    val logger: Logger = LoggerFactory.getLogger(FavoritesController::class.java)

    @Autowired
    private lateinit var visitorService: VisitorService
    @Autowired
    private lateinit var propertyService: PropertyService


    @PostMapping
    fun saveFavorite(@RequestParam idProperty: Long, request: HttpServletRequest) {
        val visitor = visitorService.getVisitor(request)
        Mono.just(visitor)
                .publishOn(Schedulers.elastic())
                .doOnNext { propertyService.saveFavorite(visitor.id!!, idProperty) }
    }

    @GetMapping
    fun getFavorites(request: HttpServletRequest): Flux<PropertyDto> {
        var properties: List<PropertyDto> = emptyList();
        val visitor = visitorService.getVisitor(request)
        properties = propertyService.getFavorites(visitor.id!!)
        return Flux.fromIterable(properties)
    }

    @DeleteMapping
    fun deleteFavorite(@RequestParam idProperty: Long, request: HttpServletRequest) {
        Mono.just(idProperty).publishOn(Schedulers.elastic()).doOnNext {
            propertyService.deleteFavorite(idFavorite = idProperty)
        }
    }


}
