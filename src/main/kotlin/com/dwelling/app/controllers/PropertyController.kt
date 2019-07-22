package com.dwelling.app.controllers

import com.dwelling.app.domain.*
import com.dwelling.app.repository.PropertyRepository
import com.dwelling.app.services.SearchService
import org.apache.http.entity.ContentType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
class PropertyController {

    @Autowired
    private lateinit var propertyRepository: PropertyRepository

    @Autowired
    private lateinit var searchService: SearchService<Property>

    @GetMapping(path = ["/create-property"])
    @ResponseBody
    fun createProperty(
    ): List<Property> {


        val visitor = Visitor(1, "FARITH", "FARITH", "SANMIGUEL", "FESANMIGUEL@MISENA.EDU.CO", 24, LocalDate.now(), "1123456", "123123123", "www.site.com", true, "123456", null, null, null)
        val country = Country(1, "Colombia")
        val city = City(1, "Bogota", country)
        val propertyType = PropertyType(1, "APARTAMENTO")
        val zone = Zone(1, "CENTRO",city)
        val neighborhood = Neighborhood(1, "CENTRO", zone)
        val image = Image(1, "IMG1", "url_imagen", true)
        val contact = Contact(1, null, null)


        val property = Property(1, visitor,propertyType, "Apartamento prueba", neighborhood, "Descripcion prueba", listOf(image), contact, 2, 1_000_000.0, 100_000_000_000.0, 23, 2, 2, 1, 1, null, null, null, 0, 0)


        return listOf(property)
    }

    @PostMapping(path = ["/load"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun bulkLoad(@RequestBody properties : List<Property> ) = propertyRepository.saveAll(properties)

    @GetMapping("/obtener")
    fun queryAll() =  propertyRepository.findAll()

    @PostMapping("/persist")
    fun persintInElastic(){
        for (property in propertyRepository.findAll()) {
            searchService.create(property)

        }

    }


}
