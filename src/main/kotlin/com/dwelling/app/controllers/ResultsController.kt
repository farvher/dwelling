package com.dwelling.app.controllers

import com.dwelling.app.domain.Property
import com.dwelling.app.services.SearchService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class ResultsController (val searchService: SearchService<String>){



    @GetMapping("/result")
    fun getProperties(): List<String>{
        searchService.create("farith")
        return searchService.searchByQueryString("")
    }

}
