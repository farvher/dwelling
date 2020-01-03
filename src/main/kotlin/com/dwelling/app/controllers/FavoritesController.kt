package com.dwelling.app.controllers

import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.services.PropertyService
import com.dwelling.app.services.VisitorService
import org.apache.coyote.Response
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import javax.servlet.http.HttpServletRequest

/**
 * RestController handle visitor properties favorites
 * @author fsanmiguel
 * */
@RestController
class FavoritesController {

    val logger: Logger = LoggerFactory.getLogger(FavoritesController::class.java)

    @Autowired
    private lateinit var visitorService: VisitorService
    @Autowired
    private lateinit var propertyService: PropertyService


    @PostMapping("/favorites/save/{idProperty}")
    fun saveFavorite(@PathVariable idVisitor: Long, @PathVariable idProperty: Long, request: HttpServletRequest)
            : Mono<ResponseEntity<String>> {
        val visitor = visitorService.getVisitor(request)
        propertyService.saveFavorite(visitor.id!!, idProperty)
        return Mono.fromCallable { ResponseEntity.ok().body("Ok") }

        return Mono.fromCallable { ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No Authorized!") }
    }

    @GetMapping("/favorites/getAll")
    fun getFavorites(request: HttpServletRequest): Mono<ResponseEntity<List<PropertyDto>>> {
        var properties: List<PropertyDto> = emptyList();
        val visitor = visitorService.getVisitor(request)
        properties = propertyService.getFavorites(visitor.id!!)
        return Mono.fromCallable { ResponseEntity.ok().body(properties) }

        return Mono.fromCallable { ResponseEntity.ok().body(properties) }
    }

    @PostMapping("/favorites/delete/{idFavorite}")
    fun deleteFavorite(@PathVariable idFavorite: Long, request: HttpServletRequest)
            : Mono<ResponseEntity<String>> {
        propertyService.deleteFavorite(idFavorite = idFavorite)
        return Mono.fromCallable { ResponseEntity.ok().body("Ok") }
        return Mono.fromCallable { ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No Authorized!") }
    }


}
