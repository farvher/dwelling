package com.dwelling.app.controllers

import com.dwelling.app.dto.LocationsDto
import com.dwelling.app.services.LocationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono


@RestController
class LocationController {


    @Autowired
    private lateinit var locationService: LocationService

    @GetMapping("/locations.js")
    fun getLocations(): Mono<ResponseEntity<LocationsDto>> {
        return ResponseEntity.ok().body(locationService.locations()).toMono()
    }

    @GetMapping("/locations-{keyword}.js")
    fun getLocations(@PathVariable keyword: String): Mono<ResponseEntity<LocationsDto>> {
        val locations = locationService.locations().copy()
        locations.locations = locations.locations.filter { l ->
            l.location.contains(keyword, true) || l.zone.contains(keyword, true) || l.city.contains(keyword, true)
        }
        return ResponseEntity.ok().body(locations).toMono()
    }

}
