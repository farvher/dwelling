package com.dwelling.app

import com.dwelling.app.services.locations.LocationsService
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import reactor.core.publisher.toMono

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LocationsTests {

    val logger: Logger = LoggerFactory.getLogger(LocationsTests::class.java)

    private val credentials = mapOf("username" to "admin", "password" to "admin")

    @Autowired
    private lateinit var locationsService: LocationsService

    @Test
    fun shouldFindCity() {
        val name = "Bogotá D.C."
        val city = locationsService.findCityByName(name)
        assert(city.name == name)
    }

    @Test
    fun shouldFindNeighborhood() {
        val name = "Lomas"
        val neighborhood = locationsService.findNeighborhoodByName(name)
        assert(neighborhood.name == name)
    }

    @Test
    fun shouldFindCZone() {
        val name = "Norte"
        val zone = locationsService.findZoneByName(name)
        assert(zone.name == name)
    }

}