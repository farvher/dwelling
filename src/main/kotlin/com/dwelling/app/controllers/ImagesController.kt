package com.dwelling.app.controllers

import com.dwelling.app.services.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest


@RestController
class ImagesController {


    @Autowired
    @Qualifier("imageMultimediaStorageService")
    private lateinit var imageMultimediaStorageService: MultimediaStorageService

    @Autowired
    private lateinit var visitorService: VisitorService

    @PostMapping("/images/upload")
    fun uploadImage(@RequestParam image : MultipartFile, @RequestParam property : String , request: HttpServletRequest) {
        var user = visitorService.getVisitor(request)
        val count = imageMultimediaStorageService.count(user.id!!,property)

        imageMultimediaStorageService.store(user.id!!,property,"imagen_123",image)
    }

}