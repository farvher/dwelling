package com.dwelling.app.dto

import com.dwelling.app.domain.*
import javax.persistence.*

class VisitorPreferencesDto
(val id: Long = -1,
 var favoritePropertyType: PropertyTypeEnum,
 var favoriteBusinessType: BusinessTypeEnum,
 var favoriteCity: String? = "",
 var favoriteZone: String? = "",
 var favoriteCountry: String? = "",
 var nearToMe: Boolean = false,
 var favoriteLocation: String? = "",
 var incomeValue: Double? = 0.0,
 var outcomeValue: Double? = 0.0,
 var idVisitor: Long,
 var withParkings: Boolean = false,
 var withPets: Boolean = false,
 var withKids: Boolean = false,
 var visitorLocations: List<VisitorLocation>? = null,
 var countPartners: Long? = 0) {

    companion object {
        fun toDto(v: VisitorPreferences): VisitorPreferencesDto = VisitorPreferencesDto(
                id = v.id,
                favoritePropertyType = v.favoritePropertyType,
                favoriteBusinessType = v.favoriteBusinessType,
                favoriteCity = v.favoriteCity.name,
                favoriteCountry = v.favoriteCountry.name,
                favoriteLocation = v.favoriteLocation,
                favoriteZone = v.favoriteZone.name,
                countPartners = v.countPartners,
                withKids = v.withKids,
                withParkings = v.withParkings,
                withPets = v.withPets,
                visitorLocations = v.visitorLocations,
                incomeValue = v.incomeValue,
                outcomeValue = v.outcomeValue,
                nearToMe = v.nearToMe,
                idVisitor = v.visitor.id
        )
    }
}
