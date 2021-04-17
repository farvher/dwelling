package com.dwelling.app.elasticsearch

import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.repository.ElasticPropertyRepository
import com.dwelling.app.services.SearchService
import org.springframework.stereotype.Service

@Service
class DwellingPersistImpl(
    private val searchService: SearchService<PropertyDto>,
    private val elasticPropertyRepository: ElasticPropertyRepository

) : DwellingPersist {
    override fun saveProperty(propertyDto: PropertyDto) {
        searchService.create(propertyDto)

    }

    override fun deleteProperty(id: Long) {
        TODO("Not yet implemented")
    }
}