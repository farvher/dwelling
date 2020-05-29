package com.dwelling.app.controllers

import com.dwelling.app.domain.Property
import com.dwelling.app.dto.EFilter
import com.dwelling.app.dto.FilterDto
import com.dwelling.app.dto.FilterType
import com.dwelling.app.elasticsearch.IDwellingsSeach
import com.dwelling.app.services.property.PropertyService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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
                       httpServletResponse: HttpServletResponse): Mono<Property> {
        logger.info("[propertyDetail] detail by id $id")
        val property = propertyService.findPropertyById(id)
        logger.info("[propertyDetail] found $property")
        return Mono.just(property!!)
    }

    @GetMapping(path = ["/property/index/{id}"])
    fun propertyDetailInIndex(@PathVariable id: Long,
                       httpServletRequest: HttpServletRequest,
                       httpServletResponse: HttpServletResponse): Mono<Property> {
        logger.info("[propertyDetailInIndex] detail by id $id")
        val properties =  dwellingsSearch.findByFilters(listOf(FilterDto(EFilter.ID,id, filterType = FilterType.KEYWORD)))
        val property = when(properties.isNotEmpty()){
            true -> properties.first()
            else -> null
        }
        logger.info("[propertyDetailInIndex] found $property")
        return Mono.just(property!!)
    }
}
