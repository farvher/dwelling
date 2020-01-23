package com.dwelling.app

import com.dwelling.app.domain.Property
import com.dwelling.app.repository.PropertyRepository
import com.dwelling.app.security.repository.UserRepository
import com.dwelling.app.services.SearchService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
class AppApplicationTests {

    @Autowired
    private lateinit var searchService: SearchService<Property>

    @Autowired
    private lateinit var propertyRepository: PropertyRepository

    @Autowired
    private lateinit var userRepository: UserRepository


    @LocalServerPort
    var randomServerPort = 0

    private lateinit var webTestClient: WebTestClient

    @Before
    fun webTestClient() {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:$randomServerPort")
                .build()
        val properties = searchService.searchByQueryString("Lorem")
        properties.forEach {
            propertyRepository.save(it)
        }
    }

    @Test
    fun shouldBe3Users() {
        assert(userRepository.findAll().size == 3)
    }

    @Test
    fun homeShouldBeSecured() {
        webTestClient.post().uri("/")
                .exchange()
                .expectStatus()
                .isUnauthorized()
    }

}

