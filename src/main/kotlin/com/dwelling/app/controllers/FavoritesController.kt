package com.dwelling.app.controllers

import com.dwelling.app.services.PropertyService
import com.dwelling.app.services.VisitorService
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import javax.servlet.http.HttpServletRequest

@RestController
class FavoritesController {


    @Autowired
    private lateinit var visitorService: VisitorService
    @Autowired
    private lateinit var propertyService: PropertyService


    @PostMapping("/favorites/{idVisitor}/{idProperty}")
    fun saveFavorite(@PathVariable idVisitor: Long, @PathVariable idProperty: Long, request: HttpServletRequest): Mono<ResponseEntity<String>> {
        if (visitorService.canIDoThatOperationOnMyUser(idVisitor, request)) {
            TODO("guardar el favorito")
            return ResponseEntity.ok().body("").toMono()
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized").toMono()
    }

    @GetMapping("/favorites/{idVisitor}")
    fun getFavorites():Mono<ResponseEntity<String>>{

        TODO("obtener favoritos")
        return ResponseEntity.ok().body("").toMono();
    }
}
