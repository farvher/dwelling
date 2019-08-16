package com.dwelling.app.dto

import com.dwelling.app.domain.*
import java.time.LocalDate
import javax.persistence.*

class PropertyDto(
        val id: Long,
        var propertyTypes: List<PropertyTypeEnum>? = null,
        var title: String = "",
        var neighborhood: String = "",
        var city: String = "",
        var zone: String = "",
        var country: String = "",
        var description: String = "",
        var imageCount: Int = 0,
        var images: List<String>? = null,
        var antiquitiy: String = "",
        var rentPrice: Double = 0.0,
        var sellPrince: Double = 0.0,
        var area: Int = 0,
        var areaUnit: String = "m2",
        var rooms: Int = 0,
        var stratum: Int = 0,
        var buildTime: Int = 0,
        var bathroom: Int = 0,
        var parking: Int = 0,
        var admin: Double = 0.0,
        var floor: Int = 0,
        var additional: List<Additional>? = null,
        var visitorId: Long,
        var longitude: Double = 0.0,
        var latitude: Double = 0.0


) {


    companion object {
        // TODO("faltan propiedades")
        fun toDto(p: Property): PropertyDto = PropertyDto(

                id = p.id,
                title = p.title,
                neighborhood = p.neighborhood.name,
                zone = p.neighborhood.zone.name,
                city = p.neighborhood.zone.city.name,
                country = p.neighborhood.zone.city.country.name,
                description = p.description,
                visitorId = p.visitor.id
        )


    }


}
