package com.dwelling.app.repository

import com.dwelling.app.domain.City
import com.dwelling.app.domain.Country
import com.dwelling.app.domain.Neighborhood
import com.dwelling.app.domain.Zone
import com.dwelling.app.dto.LocationsDto
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path
import javax.annotation.PostConstruct

@Component
class LocationsRepository {

    lateinit var locations: LocationsDto

    @Autowired
    private lateinit var countryRepository: CountryRepository

    @Autowired
    private lateinit var neighborhoodRepository: NeighborhoodRepository

    @Autowired
    private lateinit var zoneRepository: ZoneRepository

    @Autowired
    private lateinit var cityRepository: CityRepository


    @PostConstruct
    fun init() {
        val locationsString = Files.readString(Path.of(LocationsRepository.LOCATIONS_FILE), Charsets.UTF_8)
        locations = Gson().fromJson(locationsString, LocationsDto::class.java)
        locations.locations = locations.locations.sortedBy { l -> l.category }
        val country = Country(1, "Colombia")
        countryRepository.save(country)
        locations.locations.forEach {
            var city = cityRepository.findByName(it.city).orElse(City(-1, it.city, country))
            city = cityRepository.save(city)
            var zone = zoneRepository.findByName(it.zone).orElse(Zone(-1, it.zone, city))
            zone = zoneRepository.save(zone)
            val neighborhood = neighborhoodRepository.findByName(it.location).orElse(Neighborhood(-1, it.location, zone))
            neighborhoodRepository.save(neighborhood)
        }


    }

    companion object {
        final const val LOCATIONS_FILE = "./src/main/resources/static/locations.js";
    }
}
