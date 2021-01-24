package com.dwelling.app.controllers

import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.services.VisitorService
import com.dwelling.app.services.multimedia.StorageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
class PublishController {

    val logger: Logger = LoggerFactory.getLogger(PublishController::class.java)

    @Autowired
    private lateinit var visitorService: VisitorService


    @Autowired
    @Qualifier("azureBlobStorageService")
    private lateinit var azureBlobStorageService: StorageService


    @PostMapping("/publish")
    fun createProperty(request: ServerHttpRequest, @RequestBody property: PropertyDto) {

        val jwtUser = visitorService.getVisitor(request)

    }


    @PostMapping("/create-property")
    fun postProperty(
        @RequestBody property: PropertyDto,
        @RequestParam multipartFile: MultipartFile,
        request: ServerHttpRequest
    ) {
        val visitor = visitorService.getVisitor(request)
        //val visitorId = visitorService.getVisitor(idVisitor = visitor.id!!).block()
        // val property = propertyService.saveProperty(property, visitorId)
        // val path = "${visitorId!!.id}/${property.id}/image001.png"
        // azureBlobStorageService.storage(multipartFile, path)


    }
}
