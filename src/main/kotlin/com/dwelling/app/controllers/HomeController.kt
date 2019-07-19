package com.dwelling.app.controllers

import com.dwelling.app.domain.City
import com.dwelling.app.domain.Visitor
import com.dwelling.app.repository.VisitorRepository
import com.dwelling.app.services.SearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody

import org.springframework.web.reactive.function.server.body
import org.springframework.web.servlet.function.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.time.Duration
import javax.sql.DataSource
import java.time.Duration.ofMillis




@Controller
class HomeController {

    @Autowired
    lateinit var visitorRepository: VisitorRepository

    @Autowired
    lateinit var dataSource: DataSource







}
