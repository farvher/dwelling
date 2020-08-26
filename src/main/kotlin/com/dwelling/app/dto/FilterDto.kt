package com.dwelling.app.dto

import java.io.Serializable

/**
 * dto for searchlybox queries
 * */
data class FilterDto (
        val filterKey: EFilter,
        val filterValue: Any? = null,
        val filterRange: List<Any> = emptyList(),
        val filterType: FilterType = FilterType.KEYWORD): Serializable

/**
 * range : area,rooms,buildtime,bathrooms,parking,admon,floor,rentPrice,sellPrice,stratrum
 * order : imageCount,max_score
 * keyword : propertyTypes.id, neighborhood.zone.city.id, id
 * text : neighborhood.name, neighborhood.zone.name,description , additional.name, title
 * geo : longitude,latitude
 * */
enum class FilterType {
    RANGE, KEYWORD, TEXT, GEO, ORDER
}

enum class EFilter(val key:String) {
    ID("id"),
    AREA("area"),
    ROOMS("rooms"),
    BUILD_TIME("buildTime"),
    BATHROOMS("bathroom"),
    PARKING("parking"),
    ADMIN("admin"),
    FLOOR("floor"),
    RENT_PRICE("rentPrice"),
    SELL_PRICE("sellPrice"),
    STRATUM("stratum"),
    IMAGE_COUNT("imageCount"),
    PROPERTY_TYPE("propertyType"),
    BUSINESS_TYPE("businessType"),
    CITY("city"),
    ZONE("zone"),
    COUNTRY("country"),
    DESCRIPTION("description"),
    ADDITIONAL("additional"),
    TITLE("title"),
    LATITUDE("latitude"),
    LONGITUDE("longitude"),
    LOCATION("location"),
    NEIGHBORHOOD("neighborhood")


}
