package com.dwelling.app.dto

/**
 * dto for searchlybox queries
 * */
class FilterDto(
        val filterKey: String,
        val filterValue: Any,
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
    RANGE, KEYWORD, TEXT, GEO, ORDER
}

enum class EFilter(val value:String) {
    AREA("area"),
    ROOMS("rooms"),
    BUILD_TIME("buildtime"),
    BATHROOMS("bathrooms"),
    PARKING("parking"),
    ADMIN("admon"),
    FLOOR("floor"),
    RENT_PRICE("rentPrice"),
    SELL_PRICE("sellPrice"),
    STRATUM("stratrum"),
    IMAGE_COUNT("imageCount"),
    PROPERTY_TYPE("propertyTypes.id"),
    CITY("neighborhood.zone.city.id"),
    DESCRIPTION("description"),
    ADDITIONAL("additional.name"),
    TITLE("title"),
    LATITUDE("latitude"),
    LONGITUDE("longitude"),
    NEIGHBORHOOD("neighborhood.name")
}
