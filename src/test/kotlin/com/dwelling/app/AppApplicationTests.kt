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
    private lateinit var visitorRepository: VisitorRepository

    @Autowired
    private lateinit var favoritesRepository: FavoritesRepository


    @Autowired
    private lateinit var cityRepository: CityRepository

    @Autowired
    private lateinit var propertyRepository: PropertyRepository

    @Autowired
    private lateinit var userRepository: UserRepository


    @LocalServerPort
    var randomServerPort = 0

    private lateinit var webTestClient: WebTestClient

    private lateinit var webClient: WebClient

    private lateinit var token : String

    @Before
    fun webTestClient() {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:$randomServerPort")
                .build()
        webClient = WebClient.builder()
                .baseUrl("http://localhost:$randomServerPort")
                .build()
        val mockdata = Files.readString(Path.of("./data_json.json"), Charsets.UTF_8)
        val listType = object : TypeToken<ArrayList<Property?>?>() {}.type
        val strategy = object : ExclusionStrategy {
            override fun shouldSkipField(field: FieldAttributes): Boolean {
                if (field.declaringClass == Visitor::class.java && field.name == "creationDate") {
                    return true
                }
                return false
            }
            override fun shouldSkipClass(clazz: Class<*>): Boolean {
                return false
            }
        }
        val gson = GsonBuilder().addDeserializationExclusionStrategy(strategy).create()
        val properties: List<Property> = gson.fromJson("$mockdata", listType)
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

    /**
     * Un usuario puede crear favoritos , pero al eliminar estos favoritos no deberia eliminarse sus datos child
     * */
    @Test
    fun shouldNOTDeleteCities() {

        val property = propertyRepository.findById(1)
        val visitor = visitorRepository.findById(1);
        val city = property.get().neighborhood.zone.city
        val totalCities = cityRepository.count()
        favoritesRepository.save(VisitorFavorite(-1, property.get(), visitor.get()))
        assert(favoritesRepository.count() == 1L)
        favoritesRepository.deleteById(1)
        assert(totalCities == cityRepository.count())

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
    fun getOneDetail(){
        webClient.get()
                .uri("/property/detail/1")
                .retrieve()
                .bodyToMono(Property::class.java)
                .subscribe{ println(it)}

    }

}

