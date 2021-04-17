package com.dwelling.app.controllers

import com.dwelling.app.dto.ImageDto
import com.dwelling.app.services.VisitorService
import com.dwelling.app.services.multimedia.MultimediaStorageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
class ImagesController(
    @Qualifier("imageMultimediaStorageService")
    private val imageMultimediaStorageService: MultimediaStorageService,
    private val visitorService: VisitorService

) {

    val logger: Logger = LoggerFactory.getLogger(ImagesController::class.java)

    @Autowired


    @PostMapping("/images/upload")
    fun uploadImage(
        @RequestParam images: Array<MultipartFile>,
        @RequestParam property: String,
        request: ServerHttpRequest
    ): ResponseEntity<*> {

        var user = visitorService.getVisitor(request)
        logger.info("Init uploading images...")
        for ((index, image) in images.withIndex()) {
            val imageDto = ImageDto(index.toLong() + 1, 1, property, image)
            imageMultimediaStorageService.store(imageDto)
            if (!imageDto.url.isNullOrEmpty()) {
                logger.info(imageDto.url)
            }
        }
        logger.info("End uploading images...")
        return ResponseEntity.ok().body("")
    }

}
