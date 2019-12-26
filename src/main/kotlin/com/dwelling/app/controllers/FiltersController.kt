package com.dwelling.app.controllers

import com.dwelling.app.dto.EFilter
import com.dwelling.app.dto.FilterDto
import org.springframework.stereotype.Controller
import java.util.logging.Filter


/**
 * Construye filtros necesarios para el servicio de busqueda
 * @author fsanmiguel
 * */
@Controller
class FiltersController {


    /**
     * Construye una serie de filtros por frase
     * */
    fun buildFiltersByPhrase(word: String): List<FilterDto> {

        return listOf()
    }

    fun buildFilters(propertyType: String, businessType: String, location: String): List<FilterDto> {

        var propertyTypeFilter = FilterDto(EFilter.PROPERTY_TYPE, propertyType)
        var businessTypeFilter = FilterDto(EFilter.BUSINESS_TYPE,businessType)
        //var locationFilter = FilterDto(EFilter.)
        return listOf(propertyTypeFilter,businessTypeFilter)
    }


}