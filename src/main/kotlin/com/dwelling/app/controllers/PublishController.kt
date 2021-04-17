package com.dwelling.app.controllers

import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.elasticsearch.DwellingPersist
import com.dwelling.app.elasticsearch.DwellingSearch
import com.dwelling.app.services.multimedia.StorageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
class PublishController(
    @Qualifier("azureBlobStorageService")
    private val dwellingsSearchImpl: DwellingSearch,
    private val dwellingPersist: DwellingPersist
) {

    val logger: Logger = LoggerFactory.getLogger(PublishController::class.java)


    @PostMapping("/publish-no-pictures")
    fun createProperty(@RequestBody property: PropertyDto) {
        dwellingPersist.saveProperty(property)
    }


}
