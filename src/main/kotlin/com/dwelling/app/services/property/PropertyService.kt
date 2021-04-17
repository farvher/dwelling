package com.dwelling.app.services.property

import com.dwelling.app.domain.Visitor
import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.elasticsearch.DwellingSearch
import com.dwelling.app.repository.VisitorRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


/**
 * detalle de inmuebles y listado de inmuebles
 * */
@Service
class PropertyService(
    private val visitorRepository: VisitorRepository,
    private val dwellingsSearch: DwellingSearch
) {

    val logger: Logger = LoggerFactory.getLogger(PropertyService::class.java)

    fun findPropertyById(id: Long): Mono<PropertyDto> {
        return dwellingsSearch.findById(id)
    }

    fun saveProperty(property: PropertyDto, visitor: Visitor) {

    }

}
