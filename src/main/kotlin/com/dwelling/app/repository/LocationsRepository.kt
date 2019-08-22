package com.dwelling.app.repository

import com.dwelling.app.dto.LocationDto
import com.dwelling.app.dto.LocationsDto
import com.google.gson.JsonObject
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import org.springframework.stereotype.Component
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import javax.annotation.PostConstruct

@Component
class LocationsRepository {

    lateinit var locations: LocationsDto

    @PostConstruct
    fun init() {
        val locationsString = Files.readString(Path.of(LocationsRepository.LOCATIONS_FILE), Charsets.UTF_8)
        locations = Gson().fromJson(locationsString, LocationsDto::class.java)
    }

    companion object {
        final const val LOCATIONS_FILE = "./src/main/resources/static/locations.js";
    }
}
