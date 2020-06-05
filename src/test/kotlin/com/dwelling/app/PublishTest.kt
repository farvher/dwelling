package com.dwelling.app

import com.dwelling.app.domain.BusinessTypeEnum
import com.dwelling.app.domain.Property
import com.dwelling.app.domain.PropertyTypeEnum
import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.repository.FavoritesRepository
import com.dwelling.app.repository.PropertyRepository
import com.dwelling.app.repository.VisitorRepository
import com.dwelling.app.security.repository.UserRepository
import com.dwelling.app.security.services.JwtAuthenticationResponse
import com.dwelling.app.services.SearchService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.BodyInserter
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import java.lang.IllegalStateException
import java.net.URI

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PublishTest {

    val logger: Logger = LoggerFactory.getLogger(PublishTest::class.java)

    private val credentials = mapOf("username" to "admin", "password" to "admin")

    @Autowired
    private lateinit var visitorRepository: VisitorRepository

    @Autowired
    private lateinit var favoritesRepository: FavoritesRepository

    @Autowired
    private lateinit var searchService: SearchService<Property>

    @Autowired
    private lateinit var propertyRepository: PropertyRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @LocalServerPort
    var randomServerPort = 0

    private lateinit var webTestClient: WebTestClient

    private lateinit var webClient: WebClient

    private lateinit var token: String

    private lateinit var restTemplate: RestTemplate

    @Before
    fun webTestClient() {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:$randomServerPort")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
        webClient = WebClient.builder()
                .baseUrl("http://localhost:$randomServerPort")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build()
        restTemplate = RestTemplate()

        val response = restTemplate
                .postForEntity("http://localhost:$randomServerPort/auth", credentials,
                        JwtAuthenticationResponse::class.java)

        if(response.statusCode.is2xxSuccessful){
            token = response.body!!.token
        }else throw IllegalStateException("Auth error")
    }

    @Test
    fun shouldPublish() {
        val property = PropertyDto(1,
                PropertyTypeEnum.APARTAESTUDIO,
                BusinessTypeEnum.ARRIENDO,
                "Apartamento economico",
                "Lomas",
                "Bogota",
                "Sur",
                "Colombia",
                "Apartamento una habitacion bien ubicado",
                1,
                listOf(),
                "2 años",
                800_000.0,
                0.0,
                40,
                "M2",
                2,
                4,
                2,
                1,
                0,
                100_000.0,
                4,
                null,
                null,
                123123131.3123,
                123123123.13213
        )
        
        webClient.post().uri("/publish")
                .header(HttpHeaders.AUTHORIZATION,"Bearer $token")
                .body(BodyInserters.fromValue(property))
                .exchange()
                .block()
        assert(propertyRepository.findById(1).isPresent)

    }


}