package com.dwelling.app.controllers

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest


@RestController
class ImagesController {



    @PostMapping("/images/upload")
    fun uploadImage(request: HttpServletRequest) {



    }

}