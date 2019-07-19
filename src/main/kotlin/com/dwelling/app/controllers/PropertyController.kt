package com.dwelling.app.controllers

import com.dwelling.app.domain.Property
import com.dwelling.app.domain.Visitor
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class PropertyController {

    @GetMapping(path = ["/create-property"])
    @ResponseBody
    fun createProperty(

    ) : Property?{




        return null
    }




}
