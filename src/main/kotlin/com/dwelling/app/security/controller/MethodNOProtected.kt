package com.dwelling.app.security.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class MethodNOProtected {

    @GetMapping("persons")
    fun getInfo()  = listOf("1","2","3")
}
