package com.dwelling.app.controllers

import com.dwelling.app.repository.CityRepository
import com.dwelling.app.repository.VisitorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.sql.DataSource


@Controller
class HomeController {

    @GetMapping("/")
    fun home(): String {
        return "index"
    }

    @GetMapping("/token")
    fun token(): String {
        return "home"
    }

}
