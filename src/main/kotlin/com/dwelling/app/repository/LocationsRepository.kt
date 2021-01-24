package com.dwelling.app.repository

import com.dwelling.app.dto.LocationsDto
import com.google.gson.Gson
import org.springframework.stereotype.Component
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
        locations.locations = locations.locations.sortedBy { l -> l.category }


    }

    companion object {
        final const val LOCATIONS_FILE = "./src/main/resources/static/locations.js";
    }
}
