package com.dwelling.app.controllers

import com.dwelling.app.services.AzureBlobStorageService
import com.dwelling.app.services.StorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest


@RestController
class ImagesController {


    @Autowired
    @Qualifier("azureBlobStorageService")
    private lateinit var azureBlobStorageService: StorageService

    @PostMapping("/images/upload")
    fun uploadImage(request: HttpServletRequest) {

    }

}