package com.dwelling.app.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class DetailController {


    @GetMapping(path = ["/detail/{ url : .* }"])
    fun getPropertyDetail(model: Model,
                          @PathVariable url : String,
                          httpServletRequest: HttpServletRequest,
                          httpServletResponse: HttpServletResponse): String {


        return ""
    }
}