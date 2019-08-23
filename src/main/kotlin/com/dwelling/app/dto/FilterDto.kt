package com.dwelling.app.dto

/**
 * dto for searchlybox queries
 * */
class FilterDto(
        val filterKey: String,
        val filterValue :Any,
        val filterRange: List<Any> = emptyList(),
        val filterType: FilterType = FilterType.KEYWORD)

/**
 * range : area,rooms,buildtime,bathrooms,parking,admon,floor,rentPrice,sellPrice,stratrum
 * order : imageCount,max_score
 * keyword : propertyTypes.id, neighborhood.zone.city.id,
 * text : neighborhood.name, neighborhood.zone.name,description , additional.name, title
 * geo : longitude,latitude
 * */
enum class FilterType {
    RANGE, KEYWORD, TEXT,GEO,ORDER
}
