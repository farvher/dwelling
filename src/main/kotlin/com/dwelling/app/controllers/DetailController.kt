package com.dwelling.app.controllers

import com.dwelling.app.constants.Constants
import com.dwelling.app.domain.Property
import com.dwelling.app.dto.EFilter
import com.dwelling.app.dto.FilterDto
import com.dwelling.app.dto.FilterType
import com.dwelling.app.elasticsearch.IDwellingsSeach
import com.dwelling.app.services.PropertyService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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

/**
 * RestController for detail property
 *
 * @author FSanmiguel
 * */
@RestController
class DetailController {

    val logger: Logger = LoggerFactory.getLogger(DetailController::class.java)

    @Autowired
    private lateinit var propertyService: PropertyService

    @Autowired
    private lateinit var dwellingsSearch : IDwellingsSeach



    @GetMapping(path = ["/property/detail/{id}"])
    fun propertyDetail(@PathVariable id: Long,
                       httpServletRequest: HttpServletRequest,
                       httpServletResponse: HttpServletResponse): Mono<ResponseEntity<Property?>> {
        logger.info("[propertyDetail] detail by id $id")
        val property = propertyService.findPropertyById(id)
        logger.info("[propertyDetail] found $property")
        return ResponseEntity.ok().body(property).toMono()
    }

    @GetMapping(path = ["/property/index/{id}"])
    fun propertyDetailInIndex(@PathVariable id: Long,
                       httpServletRequest: HttpServletRequest,
                       httpServletResponse: HttpServletResponse): Mono<ResponseEntity<Property?>> {
        logger.info("[propertyDetailInIndex] detail by id $id")
        val properties =  dwellingsSearch.findByFilters(listOf(FilterDto(EFilter.ID,id, filterType = FilterType.KEYWORD)))
        val property = when(properties.isNotEmpty()){
            true -> properties.first()
            else -> null
        }
        logger.info("[propertyDetailInIndex] found $property")
        return ResponseEntity.ok().body(property).toMono()
    }
}
