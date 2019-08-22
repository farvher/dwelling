package com.dwelling.app.services

import com.dwelling.app.domain.City
import com.dwelling.app.domain.Country
import com.dwelling.app.domain.Neighborhood
import com.dwelling.app.domain.Zone
import com.dwelling.app.dto.LocationsDto
import com.dwelling.app.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

/**
 * Location service extracted from database and a json file
 * @author fsanmiguel
 * */
@Service
class LocationService {


    @Autowired
    private lateinit var locationsRepository: LocationsRepository
    @Autowired
    private lateinit var countryRepository: CountryRepository
    @Autowired
    private lateinit var neighborhoodRepository: NeighborhoodRepository
    @Autowired
    private lateinit var zoneRepository: ZoneRepository
    @Autowired
    private lateinit var cityRepository: CityRepository

    @Cacheable("locations")
    fun locations(): LocationsDto = locationsRepository.locations

    @Cacheable("cities")
    fun findCityByName(name: String): City {
        return cityRepository.findByName(name).orElseThrow(::Exception)
    }

    @Cacheable("zones")
    fun findZoneByName(name: String): Zone {
        return zoneRepository.findByName(name).orElseThrow(::Exception)
    }

    @Cacheable("neighborhoods")
    fun findNeighborhoodByName(name: String): Neighborhood {
        return neighborhoodRepository.findByName(name).orElseThrow(::Exception)
    }

    @Cacheable("countries")
    fun findCountryByName(name: String): Country {
        return countryRepository.findByName(name).orElseThrow(::Exception)
    }


}
