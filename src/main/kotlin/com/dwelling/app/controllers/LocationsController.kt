package com.dwelling.app.controllers

import com.dwelling.app.dto.LocationsDto
import com.dwelling.app.services.LocationsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono


/**
 * RestController for commons locations
 *
 * @author FSanmiguel
 * */
@RestController
class LocationsController {


    @Autowired
    private lateinit var locationService: LocationsService

    @GetMapping("/locations.js")
    fun getLocations(): Mono<ResponseEntity<LocationsDto>> {
        return ResponseEntity.ok().body(locationService.locations()).toMono()
    }

    @GetMapping("/locations-{keyword}.js")
    fun getLocations(@PathVariable keyword: String): Mono<ResponseEntity<LocationsDto>> {
        val locations = locationService.locations().copy()
        locations.locations = locations.locations.filter {
            it.location.contains(keyword, true) || it.zone.contains(keyword, true) || it.city.contains(keyword, true)
        }
        return ResponseEntity.ok().body(locations).toMono()
    }

    @GetMapping("/cities-{keyword}.js")
    fun getCities(@PathVariable keyword: String): Mono<ResponseEntity<LocationsDto>> {
        val locations = locationService.locations().copy()
        locations.locations = locations.locations.filter {
           it.city.contains(keyword, true) && it.category==1
        }
        return ResponseEntity.ok().body(locations).toMono()
    }



}
