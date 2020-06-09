package com.dwelling.app

import com.dwelling.app.domain.Property
import com.dwelling.app.domain.Visitor
import com.dwelling.app.domain.VisitorFavorite
import com.dwelling.app.repository.CityRepository
import com.dwelling.app.repository.FavoritesRepository
import com.dwelling.app.repository.PropertyRepository
import com.dwelling.app.repository.VisitorRepository
import com.dwelling.app.security.repository.UserRepository
import com.dwelling.app.services.SearchService
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.client.WebClient
import java.nio.file.Files
import java.nio.file.Path
import java.util.*


@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppApplicationTests {

    val logger: Logger = LoggerFactory.getLogger(AppApplicationTests::class.java)

    private val credentials = mapOf("username" to "admin", "password" to "admin")

    @Autowired
    private lateinit var userRepository: UserRepository

    @LocalServerPort
    var randomServerPort = 0

    private lateinit var webTestClient: WebTestClient

    private lateinit var webClient: WebClient


    @Before
    fun webTestClient() {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:$randomServerPort")
                .build()
        webClient = WebClient.builder()
                .baseUrl("http://localhost:$randomServerPort")
                .build()

    }

    @Test
    fun shouldBe3Users() {
        assert(userRepository.findAll().size == 3)
    }


    @Test
    fun shouldLogin() {
        webTestClient
                .post()
                .uri("/auth")
                .bodyValue(credentials)
                .exchange()
                .expectStatus()
                .isOk
    }

    @Test
    fun shouldFindElastic() {
        val property = webClient.get()
                .uri("/property/index/1")
                .retrieve()
                .bodyToMono(Property::class.java)
                .block()
        assert(property!!.id==1L)


    }

}

