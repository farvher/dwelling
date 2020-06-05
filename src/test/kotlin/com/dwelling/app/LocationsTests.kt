package com.dwelling.app

import com.dwelling.app.services.locations.LocationsService
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LocationsTests {

    val logger: Logger = LoggerFactory.getLogger(LocationsTests::class.java)

    private val credentials = mapOf("username" to "admin", "password" to "admin")

    @Autowired
    private lateinit var locationsService: LocationsService

    @Test
    fun shouldFindCity(){
        val city = locationsService.findCityByName("Cali")
    }

}