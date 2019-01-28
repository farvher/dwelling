package com.dwelling.app.controllers

import com.dwelling.app.domain.Visitor
import com.dwelling.app.repository.VisitorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono


@Controller
class HomeController {

    @Autowired
    lateinit var visitorRepository: VisitorRepository

    @ResponseBody
    @GetMapping(path = ["/", ""])
    fun getIndex(): Mono<List<ResponseEntity<Visitor>>> {

        return visitorRepository.findAll().map {
            ResponseEntity.ok().body(it) }
                .toMono()

    }

    @GetMapping(path=["/save/{email}"])
    fun save(@PathVariable email:String): String{

        visitorRepository.save(Visitor(name = email,email = "$email @gmail.com"))
        return "redirect:/"
    }


}