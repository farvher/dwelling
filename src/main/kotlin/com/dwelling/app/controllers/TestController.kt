package com.dwelling.app.controllers

import com.dwelling.app.domain.*
import com.dwelling.app.dto.EFilter
import com.dwelling.app.dto.FilterDto
import com.dwelling.app.dto.FilterType
import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.repository.*
import com.dwelling.app.security.model.User
import com.dwelling.app.security.repository.UserRepository
import com.dwelling.app.services.SearchService
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import javax.sql.DataSource

@RestController()
class TestController {
    private var H2_CONSOLE = "jdbc:h2:mem:testdb"
    @Autowired
    private lateinit var  dataSource: DataSource

    @Autowired
    private lateinit var visitorPreferencesRepository: VisitorPreferencesRepository
    @Autowired
    private lateinit var favoritesRepository: FavoritesRepository

    @Autowired
    private lateinit var propertyRepository: PropertyRepository

    @Autowired
    private lateinit var searchService: SearchService<PropertyDto>

    @Autowired
    private lateinit var visitorRepository: VisitorRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var elasticPropertyRepository : ElasticPropertyRepository


    @GetMapping("/test/dummy")
    @ResponseBody
    fun createProperty(
    ): List<PropertyDto> {

        var contact = Contact(1,"wendy mantilla","emantilla@gmail.com","www.site.com","calle 123",null,null,"1234556",null,null)
        var realState = RealState(1,"inmobiliaria",contact)
        val visitor = Visitor(1, "FARITH", "FARITH", "SANMIGUEL", "FESANMIGUEL@MISENA.EDU.CO", 24, LocalDate.now(), "1123456", "123123123", "www.site.com", true, null, realState )
        val country = Country(1, "Colombia")
        val city = City(1, "Bogota", country)
        val propertyType = PropertyTypeEnum.APARTAESTUDIO

        val zone = Zone(1, "CENTRO",city)
        val neighborhood = Neighborhood(1, "CENTRO", zone)
        val image = Image(1, "IMG1", "url_imagen", true)
        val image2 = Image(2, "IMG2", "url_imagen2", true)
        val additional = Additional(1,"Cancha de futbol")
        val additional2 = Additional(2,"Gimnasio")
        val property = Property(1, propertyType,BusinessTypeEnum.ARRIENDO, "Apartamento prueba", neighborhood, "Descripcion prueba",4, listOf(image,image2), "4 a 5 años",  1_000_000.0, 100_000_000_000.0, 23, "m2", 2, 2, 1, 1,1, 50_000.0, 2, listOf(additional,additional2), visitor, Location(1,1.0,2.0))


        return listOf(PropertyDto.toDto(property))
    }

    @GetMapping("/test/all")
    fun queryAll() =  propertyRepository.findAll()

    @PostMapping("/test/persist")
    fun persintInElastic(){
        for (property in propertyRepository.findAll()) {
            searchService.create(PropertyDto.toDto(property))
        }
    }
    @PostMapping("/test/extract")
    fun persistFromElastic(){
        val properties = searchService.searchByQueryString("Lorem")
        for( p in properties){
           // propertyRepository.save(PropertyDto.toDomain(p))
        }
    }

    @GetMapping(value = ["/test/search"])
    fun getElastic() = searchService.searchByQueryString("Lorem")

    @GetMapping("/test/search/{word}")
    fun getElastic2(@PathVariable word:String) = searchService.searchByQueryString(word)

    @GetMapping("/test/detail/{id}")
    fun getOne(@PathVariable id:Long) = propertyRepository.findById(id).get()

    @GetMapping("/test/filter")
    fun getSampleFilter() : FilterDto{
        return  FilterDto(EFilter.BATHROOMS,2,filterRange = listOf(1,3), filterType = FilterType.RANGE)
    }
    @GetMapping("/test/filters")
    fun getSampleFilters() : List<FilterDto>{
        return  listOf(FilterDto(EFilter.BATHROOMS,2,filterRange = listOf(1,3), filterType = FilterType.RANGE))
    }

    @GetMapping("/test/visitors")
    fun getVisitors() : List<Visitor> = visitorRepository.findAll()
    @GetMapping("/test/users")
    fun getUsers() : List<User> = userRepository.findAll()
    @GetMapping("/test/favorites")
    fun getFavorites() : List<VisitorFavorite>{
        return favoritesRepository.findAll();
    }
    @GetMapping("/test/preferences")
    fun getPreferences() : List<VisitorPreferences> {
        return visitorPreferencesRepository.findAll();
    }

    @PostMapping("/test/elastic")
    fun loadElastic(@RequestBody body : String): String {

        searchService.createIndexIfNotExists("ptest")

        val mockdata = body
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
        var properties: List<Property> = gson.fromJson("$mockdata", listType)
        properties.forEach {
            elasticPropertyRepository.save(PropertyDto.toDto(it))
        }

        return "";
    }


}
