package com.dwelling.app.services

import com.dwelling.app.domain.Property
import com.dwelling.app.domain.Visitor
import com.dwelling.app.domain.VisitorFavorite
import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.repository.FavoritesRepository
import com.dwelling.app.repository.PropertyRepository
import com.dwelling.app.repository.VisitorRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


/**
 * detalle de inmuebles y listado de inmuebles
 * */
@Service
class PropertyService {

    val logger: Logger = LoggerFactory.getLogger(PropertyService::class.java)

    @Autowired
    private lateinit var visitorRepository: VisitorRepository
    @Autowired
    private lateinit var favoritesRepository: FavoritesRepository
    @Autowired
    private lateinit var propertyRepository: PropertyRepository


    fun findPropertyById(id: Long): Property? {
        return propertyRepository.findById(id).orElse(null)
    }

    fun saveProperty(property: PropertyDto, visitor: Visitor) {
        propertyRepository.save(PropertyDto.toDomain(property,visitor))
    }

    fun updateProperty(property: Property) {
        propertyRepository.save(property)
    }

    fun deleteProperty(id: Long) {
        propertyRepository.deleteById(id)
    }

    fun getFavorites(idVisitor: Long): List<PropertyDto> {
        return favoritesRepository.findByVisitor(visitorRepository.getOne(idVisitor)).orElse(emptyList()).map { p -> PropertyDto.toDto(p.property) }.toList()
    }

    fun saveFavorite(idVisitor: Long, idProperty: Long) {
        var property = propertyRepository.findById(idProperty).orElseThrow { Exception("Property not found") }
        var visitor = visitorRepository.findById(idVisitor).orElseThrow { Exception("Visitor not found") }
        var visitorFavorite = VisitorFavorite(-1, property, visitor);
        favoritesRepository.save(visitorFavorite)
    }

    fun deleteFavorite(idFavorite: Long) {
        favoritesRepository.deleteById(idFavorite);
    }

}
