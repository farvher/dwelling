package com.dwelling.app.services

import com.dwelling.app.domain.Property
import com.dwelling.app.domain.Visitor
import com.dwelling.app.domain.VisitorFavorite
import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.repository.FavoritesRepository
import com.dwelling.app.repository.PropertyRepository
import com.dwelling.app.repository.VisitorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


/**
 * detalle de inmuebles y listado de inmuebles
 * */
@Service
class PropertyService {

    @Autowired
    private lateinit var visitorRepository: VisitorRepository
    @Autowired
    private lateinit var favoritesRepository: FavoritesRepository
    @Autowired
    private lateinit var propertyRepository: PropertyRepository


    fun findPropertyById(id: Long): Property? {
        return propertyRepository.findById(id).orElse(null)
    }

    fun saveProperty(property: Property) {
        propertyRepository.save(property)
    }

    fun updateProperty(property: Property) {
        propertyRepository.save(property)
    }

    fun deleteProperty(id: Long) {
        propertyRepository.deleteById(id)
    }

    fun getFavorites(idVisitor: Long): List<PropertyDto> {
        return favoritesRepository.findByVisitor(idVisitor).map { p -> PropertyDto.toDto(p.property) }.toList()
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
