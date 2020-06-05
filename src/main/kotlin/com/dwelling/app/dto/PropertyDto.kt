package com.dwelling.app.dto

import com.dwelling.app.domain.*
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotNull

class PropertyDto(
        val id: Long?,
        var propertyType: PropertyTypeEnum,
        var businessType: BusinessTypeEnum,
        var title: String,
        //location
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
        var additional: List<Additional>? = null,
        var visitorId: Long?,
        @NotNull
        var longitude: Double,
        @NotNull
        var latitude: Double


) {


    companion object {
        fun toDto(p: Property): PropertyDto = PropertyDto(
                id = p.id,
                title = p.title,
                propertyType = p.propertyType,
                businessType = p.businessType,
                neighborhood = p.neighborhood.name,
                zone = p.neighborhood.zone.name,
                city = p.neighborhood.zone.city.name,
                country = p.neighborhood.zone.city.country.name,
                description = p.description,
                visitorId = p.visitor.id,
                admin = p.admon,
                antiquitiy = p.antiquitiy,
                area = p.area,
                areaUnit = p.areaUnit,
                bathroom = p.bathroom,
                buildTime = p.buildTime,
                floor = p.floor,
                imageCount = p.imageCount,
                latitude = p.location.lat,
                longitude = p.location.lon,
                parking = p.parking,
                rentPrice = p.rentPrice,
                sellPrince = p.sellPrince,
                rooms = p.rooms,
                stratum = p.stratum,
                images = p.images!!.map { i -> i.url }
        )

        fun toDomain(p: PropertyDto, v: Visitor, n: Neighborhood) = Property(
                id = p.id!!,
                propertyType = p.propertyType,
                businessType = p.businessType,
                title = p.title,
                neighborhood = Neighborhood(n.id, n.name,
                        Zone(n.zone.id, n.zone.name,
                                City(n.zone.city.id, n.zone.city.name,
                                        Country(n.zone.city.country.id, n.zone.city.country.name)))),

                description = p.description,
                imageCount = p.imageCount,
                images = null,
                antiquitiy = p.antiquitiy,
                rentPrice = p.rentPrice,
                sellPrince = p.sellPrince,
                area = p.area,
                areaUnit = p.areaUnit,
                rooms = p.rooms,
                stratum = p.stratum,
                buildTime = p.buildTime,
                bathroom = p.bathroom,
                parking = p.parking,
                admon = p.admin,
                floor = p.floor,
                additional = p.additional,
                visitor = v,
                location = Location(-1, p.longitude, p.latitude)

        )


    }


}
