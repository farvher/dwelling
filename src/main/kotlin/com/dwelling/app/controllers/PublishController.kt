package com.dwelling.app.controllers

import com.dwelling.app.domain.Property
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


@RestController
class PublishController {


    @PostMapping("/create-post")
    fun postProperty(@RequestBody property: Property) {

        val neighborhood = property.neighborhood
        val visitor = property.visitor



    }
}
