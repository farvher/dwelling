package com.dwelling.app

import com.dwelling.app.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.client.WebClient


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


    @BeforeEach
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
        //  assert(userRepository.findAll().size == 3)
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


}

