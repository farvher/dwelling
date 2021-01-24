package com.dwelling.app.dto

import com.dwelling.app.domain.BusinessTypeEnum
import com.dwelling.app.domain.PropertyTypeEnum
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.core.geo.GeoPoint


@Document(indexName = "property")
data class PropertyDto(
    @Id
    val id: Long?,
    var propertyType: PropertyTypeEnum,
    var businessType: BusinessTypeEnum,
    var title: String,
    var neighborhood: String,
    var city: String,
    var zone: String = "",
    var country: String = "",
    var description: String,
    var imageCount: Int? = 0,
    var images: List<String>,
    var antiquitiy: String = "",
    var rentPrice: Double?,
    var sellPrince: Double?,
    var area: Int,
    var areaUnit: String = "m2",
    var rooms: Int = 0,
    var stratum: Int,
    var buildTime: Int = 0,
    var bathroom: Int = 0,
    var parking: Int = 0,
    var admin: Double = 0.0,
    var floor: Int? = 0,
    var additional: List<AdditionalDto>? = null,
    var visitorId: Long?,
    val location: GeoPoint
)