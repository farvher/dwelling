package com.dwelling.app

import com.dwelling.app.domain.Property
import com.dwelling.app.dto.EFilter
import com.dwelling.app.dto.FilterDto
import com.dwelling.app.dto.FilterType
import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.elasticsearch.ElasticIndexService
import com.dwelling.app.elasticsearch.IDwellingsSeach
import com.dwelling.app.repository.PropertyRepository
import com.dwelling.app.security.controller.UserRestController
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.devtools.remote.client.HttpHeaderInterceptor
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.bodyToMono
import java.util.*


@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ResultsTests {

    val logger: Logger = LoggerFactory.getLogger(ResultsTests::class.java)

    @LocalServerPort
    var randomServerPort = 0

    private lateinit var webClient: WebClient

    val geolocation = FilterDto(
            EFilter.LOCATION,
            4,
            listOf(-60.236283, -80.218254),
            FilterType.GEO)

    val propertyType = FilterDto(
            EFilter.PROPERTY_TYPE,
            "CASA",
            emptyList(),
            FilterType.KEYWORD
    )
    val propertyType2 = FilterDto(
            EFilter.PROPERTY_TYPE,
            "CONSULTORIO",
            emptyList(),
            FilterType.KEYWORD
    )
    val businessType = FilterDto(
            EFilter.BUSINESS_TYPE,
            "VENTA",
            emptyList(),
            FilterType.KEYWORD
    )
    val city = FilterDto(
            EFilter.CITY,
            "Sterling",
            emptyList(),
            FilterType.TEXT
    )

    val zone = FilterDto(
            EFilter.ZONE,
            "ORIENTE",
            emptyList(),
            FilterType.TEXT
    )

    val neighborhood = FilterDto(
            EFilter.NEIGHBORHOOD,
            "Bushwick Avenue",
            emptyList(),
            FilterType.TEXT
    )

    val rooms = FilterDto(
            EFilter.ROOMS,
            null,
            listOf(2,3),
            FilterType.RANGE
    )
    val bathrooms = FilterDto(
            EFilter.BATHROOMS,
            null,
            listOf(1,2),
            FilterType.RANGE
    )
    val parking = FilterDto(
            EFilter.PARKING,
            null,
            listOf(0,1),
            FilterType.RANGE
    )
    val stratum = FilterDto(
            EFilter.STRATUM,
            null,
            listOf(2,3),
            FilterType.RANGE
    )


    @Before
    fun webTestClient() {
        webClient = WebClient.builder()
                .baseUrl("http://localhost:$randomServerPort")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build()
    }

    fun executeRequest(body: List<FilterDto>): ResponseEntity<List<PropertyDto>> {
       val request = Gson().toJson(body)
       return webClient.post()
                .uri("/search")
                .bodyValue(request)
                .exchange()
                .doOnError{ print("error") }
                .block()!!
                .toEntityList(PropertyDto::class.java)
               .block()!!
    }


    @Test
    fun findByGeolocations(): Unit {
        var response = executeRequest(listOf(geolocation))
        assert(response.body!!.isNotEmpty())

    }

    @Test
    fun findByPropertyTypeAndBusinessType() {
        var response = executeRequest(listOf(propertyType, businessType))
        assert(response.body!!.isNotEmpty())

    }

    @Test
    fun findByPropertyTypeAndBusinessTypeAndCity() {
        var response = executeRequest(listOf(propertyType, businessType,city))
        assert(response.body!!.isNotEmpty())


    }

    @Test
    fun findByPropertyTypeAndBusinessTypeAndZone() {
        var response = executeRequest(listOf(propertyType, businessType, zone))
        assert(response.body!!.isNotEmpty())

    }

    @Test
    fun findByPropertyTypeAndBusinessTypeAndNeighborhood() {
        var response = executeRequest(listOf(propertyType, businessType, neighborhood))
        assert(response.body!!.isNotEmpty())
    }

    @Test
    fun findByPropertyTypeAndBusinessTypeAndGeolocation() {
        var response = executeRequest(listOf(propertyType2, businessType, geolocation))
        assert(response.body!!.isNotEmpty())

    }

    @Test
    fun filterByRooms() {
        var response = executeRequest(listOf(rooms))
        assert(response.body!!.isNotEmpty())
    }

    @Test
    fun filterByStratum() {
        var response = executeRequest(listOf(stratum))
        assert(response.body!!.isNotEmpty())
    }

    @Test
    fun filterByBathrooms() {
        var response = executeRequest(listOf(bathrooms))
        assert(response.body!!.isNotEmpty())
    }

    @Test
    fun filterByParking() {
        var response = executeRequest(listOf(parking))
        assert(response.body!!.isNotEmpty())
    }
    fun filterByPrice() {}

}