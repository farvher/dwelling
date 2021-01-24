package com.dwelling.app.controllers

import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.services.property.PropertyService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


/**
 * RestController for detail property
 *
 * @author FSanmiguel
 * */
@RestController
class DetailController(
    private val propertyService: PropertyService
) {

    val logger: Logger = LoggerFactory.getLogger(DetailController::class.java)


    @GetMapping(path = ["/property/detail/{id}"])
    fun propertyDetail(
        @PathVariable id: Long,
        httpServletRequest: ServerHttpRequest,
        httpServletResponse: ServerHttpResponse
    ): Mono<PropertyDto> {
        logger.info("[propertyDetail] detail by id $id")
        val property = propertyService.findPropertyById(id)
        logger.info("[propertyDetail] found $property")
        return property
    }

}
