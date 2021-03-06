package com.dwelling.app.controllers

import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.repository.PropertyRepository
import com.dwelling.app.services.property.PropertyService
import com.dwelling.app.services.multimedia.StorageService
import com.dwelling.app.services.VisitorService
import com.dwelling.app.services.locations.LocationsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest


@RestController
class PublishController {

    val logger: Logger = LoggerFactory.getLogger(PublishController::class.java)

    @Autowired
    private lateinit var visitorService: VisitorService

    @Autowired
    private lateinit var propertyService: PropertyService

    @Autowired
    private lateinit var locationsService: LocationsService

    @Autowired
    @Qualifier("azureBlobStorageService")
    private lateinit var azureBlobStorageService: StorageService


    @PostMapping("/publish")
    fun createProperty(request: HttpServletRequest, @RequestBody property: PropertyDto) {

        val jwtUser = visitorService.getVisitor(request)
        val visitor = visitorService.getVisitor(jwtUser.id!!)
        val neighborhood = locationsService.findNeighborhoodByName(property.neighborhood)
        propertyService.saveProperty(property,visitor,neighborhood)
    }


    @PostMapping("/create-property")
    fun postProperty(@RequestBody property: PropertyDto,
                     @RequestParam multipartFile: MultipartFile,
                     request: HttpServletRequest) {
        val visitor = visitorService.getVisitor(request)
        val visitorId = visitorService.getVisitor(idVisitor = visitor.id!!)
       // val property = propertyService.saveProperty(property, visitorId)
        val path = "${visitorId.id}/${property.id}/image001.png"
        azureBlobStorageService.storage(multipartFile, path)


    }
}
