package com.dwelling.app.dto

import org.springframework.beans.factory.annotation.Value

data class LocationDto(
        var location: String,
        var neighborhood: String,
        var zone: String,
        var city: String,
        var category : Int,
        var country: String)

data class LocationsDto(
        var locations : List<LocationDto>
)
