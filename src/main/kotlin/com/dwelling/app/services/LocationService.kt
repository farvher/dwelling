package com.dwelling.app.services

import com.dwelling.app.domain.City
import com.dwelling.app.domain.Country
import com.dwelling.app.domain.Neighborhood
import com.dwelling.app.domain.Zone
import com.dwelling.app.repository.CityRepository
import com.dwelling.app.repository.CountryRepository
import com.dwelling.app.repository.NeighborhoodRepository
import com.dwelling.app.repository.ZoneRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class LocationService {


    @Autowired
    private lateinit var countryRepository: CountryRepository
    @Autowired
    private lateinit var neighborhoodRepository: NeighborhoodRepository
    @Autowired
    private lateinit var zoneRepository: ZoneRepository
    @Autowired
    private lateinit var cityRepository: CityRepository

    @Cacheable
    fun findCityByName(name : String) : City {
        return cityRepository.findByName(name).orElseThrow(::Exception)
    }
    @Cacheable
    fun findZoneByName(name :String) : Zone{
        return zoneRepository.findByName(name).orElseThrow(::Exception)
    }
    @Cacheable
    fun findNeighborhoodByName(name :String) :Neighborhood{
        return neighborhoodRepository.findByName(name).orElseThrow(::Exception)
    }
    @Cacheable
    fun findCountryByName(name:String) : Country{
        return countryRepository.findByName(name).orElseThrow(::Exception)
    }

}
