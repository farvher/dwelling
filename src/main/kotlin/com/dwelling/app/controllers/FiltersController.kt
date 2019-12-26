package com.dwelling.app.controllers

import com.dwelling.app.dto.EFilter
import com.dwelling.app.dto.FilterDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.logging.Filter


/**
 * Construye filtros necesarios para el servicio de busqueda
 * @author fsanmiguel
 * */
@RestController
class FiltersController {

    val logger: Logger = LoggerFactory.getLogger(FiltersController::class.java)
    /**
     * Construye una serie de filtros por frase
     * */
    @GetMapping("/filters/{text}")
    fun buildFiltersByText(@PathVariable text: String): List<FilterDto> {

        TODO("recorrer la frase y encontrar palabras claves para construir filtros")
        return listOf()
    }

    @PostMapping("/filters")
    fun buildBasicFilters(@RequestParam propertyType: String, @RequestParam businessType: String, @RequestParam location: String): List<FilterDto> {

        var propertyTypeFilter = FilterDto(EFilter.PROPERTY_TYPE, propertyType)
        var businessTypeFilter = FilterDto(EFilter.BUSINESS_TYPE,businessType)
        var locationFilter = FilterDto(EFilter.NEIGHBORHOOD,location)
        return listOf(propertyTypeFilter,businessTypeFilter,locationFilter)
    }

    //@PostMapping("/filters")
    fun buildAdvanceFilters(@RequestParam propertyType: String, @RequestParam businessType: String, @RequestParam location: String): List<FilterDto> {

        var propertyTypeFilter = FilterDto(EFilter.PROPERTY_TYPE, propertyType)
        var businessTypeFilter = FilterDto(EFilter.BUSINESS_TYPE,businessType)
        var locationFilter = FilterDto(EFilter.NEIGHBORHOOD,location)
        TODO("faltan mas filtros definir segun el tipo de busqueda")
        return listOf(propertyTypeFilter,businessTypeFilter,locationFilter)
    }


}