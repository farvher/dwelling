package com.dwelling.app.controllers

import com.dwelling.app.constants.Constants
import com.dwelling.app.domain.Property
import com.dwelling.app.services.PropertyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.sql.DataSource

@RestController
class DetailController {


    @Autowired
    private lateinit var propertyService: PropertyService

    @Autowired
    lateinit var dataSource: DataSource



        @GetMapping(path = ["/property/detail/{id}"])
    fun propertyDetail(@PathVariable id: Long,
                       httpServletRequest: HttpServletRequest,
                       httpServletResponse: HttpServletResponse): Mono<ResponseEntity<Property?>> {

        val property = propertyService.findPropertyById(id)

        return ResponseEntity.ok().body(property).toMono()
    }

    @PostMapping(path = ["/detail/save-property"])
    @Secured("ROLE_USER")
    fun saveProperty(@RequestBody property: Property,
                     httpServletRequest: HttpServletRequest,
                     httpServletResponse: HttpServletResponse): Mono<ResponseEntity<String>> {
        propertyService.saveProperty(property)
        return ResponseEntity.ok().body("OK").toMono()
    }
}
