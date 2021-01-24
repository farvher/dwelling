package com.dwelling.app

import com.dwelling.app.dto.EFilter
import com.dwelling.app.dto.FilterDto
import com.dwelling.app.dto.FilterType
import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.elasticsearch.ElasticIndexService
import com.dwelling.app.elasticsearch.IDwellingsSearch
import com.dwelling.app.security.controller.UserRestController
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ResultsTests {

    val logger: Logger = LoggerFactory.getLogger(ResultsTests::class.java)

    @Autowired
    private lateinit var searchService: ElasticIndexService<PropertyDto>

    @Autowired
    private lateinit var dwellingsSearch: IDwellingsSearch

    @Autowired
    private lateinit var userRestController: UserRestController

    @LocalServerPort
    var randomServerPort = 0


    private lateinit var webTestClient: WebTestClient

    private lateinit var webClient: WebClient

    private lateinit var restTemplate: RestTemplate

    @BeforeEach
    fun webTestClient() {
        restTemplate = RestTemplate()
    }


    @Test
    fun findByGeolocations(): Unit {

        var geoFilter = FilterDto(
            EFilter.LOCATION,
            "",
            listOf(-60.236283, -80.218254),
            FilterType.GEO
        )

        var response = restTemplate
            .postForEntity(
                "http://localhost:$randomServerPort/search", listOf(geoFilter),
                List::class.java
            )

        assert(response.body!!.isNotEmpty())

    }


}