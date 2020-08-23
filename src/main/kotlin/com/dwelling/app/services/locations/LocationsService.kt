package com.dwelling.app.services.locations

import com.dwelling.app.domain.City
import com.dwelling.app.domain.Country
import com.dwelling.app.domain.Neighborhood
import com.dwelling.app.domain.Zone
import com.dwelling.app.dto.LocationsDto
import com.dwelling.app.exceptions.CityNotFoundException
import com.dwelling.app.exceptions.CountryNotFoundException
import com.dwelling.app.exceptions.NeighborhoodNotFoundException
import com.dwelling.app.exceptions.ZoneNotFoundException
import com.dwelling.app.repository.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

/**
 * Location service extracted from database and a json file
 * @author fsanmiguel
 * */
@Service
class LocationsService {

    val logger: Logger = LoggerFactory.getLogger(LocationsService::class.java)


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
        return cityRepository.findByName(name).orElseThrow { CityNotFoundException(name) }
    }


    @Cacheable("zones")
    fun findZoneByName(name: String): Zone {
        return zoneRepository.findByName(name).orElseThrow{ ZoneNotFoundException(name) }
    }

    @Cacheable("neighborhoods")
    fun findNeighborhoodByName(name: String): Neighborhood {
        return neighborhoodRepository.findByName(name).orElseThrow{ NeighborhoodNotFoundException(name) }
    }

    @Cacheable("countries")
    fun findCountryByName(name: String): Country {
        return countryRepository.findByName(name).orElseThrow{ CountryNotFoundException(name) }
    }


}
