package com.dwelling.app.controllers

import com.dwelling.app.domain.*
import com.dwelling.app.dto.EFilter
import com.dwelling.app.dto.FilterDto
import com.dwelling.app.dto.FilterType
import com.dwelling.app.elasticsearch.DwellingsSearchImpl
import com.dwelling.app.elasticsearch.IDwellingsSeach
import com.dwelling.app.repository.PropertyRepository
import com.dwelling.app.repository.VisitorRepository
import com.dwelling.app.security.controller.UserRestController
import com.dwelling.app.security.model.User
import com.dwelling.app.security.repository.UserRepository
import com.dwelling.app.services.SearchService
import org.apache.http.entity.ContentType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController()
class TestController {

    @Autowired
    private lateinit var dwellingsSearch: IDwellingsSeach
    @Autowired
    private lateinit var userRestController:UserRestController

    @Autowired
    private lateinit var propertyRepository: PropertyRepository

    @Autowired
    private lateinit var searchService: SearchService<Property>

    @Autowired
    private lateinit var visitorRepository: VisitorRepository

    @Autowired
    private lateinit var userRepository: UserRepository




    @GetMapping("/test/dummy")
    @ResponseBody
    fun createProperty(
    ): List<Property> {

        var contact = Contact(1,"wendy mantilla","emantilla@gmail.com","www.site.com","calle 123",null,null,"1234556",null,null)
        var builder = Builder(1,"Constructoraa",contact)
        var realState = RealState(1,"inmobiliaria",contact)
        val visitor = Visitor(1, "FARITH", "FARITH", "SANMIGUEL", "FESANMIGUEL@MISENA.EDU.CO", 24, LocalDate.now(), "1123456", "123123123", "www.site.com", true, null, realState )
        val country = Country(1, "Colombia")
        val city = City(1, "Bogota", country)
        val propertyType = PropertyType(1, "APARTAMENTO")
        val propertyType2 = PropertyType(2, "CASA")
        val zone = Zone(1, "CENTRO",city)
        val neighborhood = Neighborhood(1, "CENTRO", zone)
        val image = Image(1, "IMG1", "url_imagen", true)
        val image2 = Image(2, "IMG2", "url_imagen2", true)
        val additional = Additional(1,"Cancha de futbol")
        val additional2 = Additional(2,"Gimnasio")
        val property = Property(1, listOf(propertyType,propertyType2), "Apartamento prueba", neighborhood, "Descripcion prueba",4, listOf(image,image2), "4 a 5 años",  1_000_000.0, 100_000_000_000.0, 23, "m2", 2, 2, 1, 1,1, 50_000.0, 2, listOf(additional,additional2), visitor, Location(1,1.0,2.0))


        return listOf(property)
    }

    @PostMapping(path = ["/test/load"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun bulkLoad(@RequestBody properties : List<Property> ) = propertyRepository.saveAll(properties)

    @GetMapping("/test/all")
    fun queryAll() =  propertyRepository.findAll()

    @PostMapping("/test/persist")
    fun persintInElastic(){
        for (property in propertyRepository.findAll()) {
            searchService.create(property)
        }

    }

    @GetMapping("/test/search")
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





}
