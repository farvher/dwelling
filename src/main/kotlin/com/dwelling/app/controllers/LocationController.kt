package com.dwelling.app.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class LocationController {

    @GetMapping("/locations")
    fun getLocations() : String{
        TODO()
        return "";
    }

}
