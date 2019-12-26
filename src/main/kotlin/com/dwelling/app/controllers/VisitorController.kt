package com.dwelling.app.controllers

import com.dwelling.app.domain.Visitor
import com.dwelling.app.domain.VisitorPreferences
import com.dwelling.app.repository.VisitorPreferencesRepository
import com.dwelling.app.repository.VisitorRepository
import com.dwelling.app.security.controller.UserRestController
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class VisitorController {

    val logger: Logger = LoggerFactory.getLogger(VisitorController::class.java)

    @Autowired
    private lateinit var visitorPreferencesRepository: VisitorPreferencesRepository

    @Autowired
    private lateinit var userRestController: UserRestController
    @Autowired
    private lateinit var visitorRepository: VisitorRepository


    @PostMapping("/visitor/update-preferences")
    fun updatePreferences(@RequestBody visitorPreferences: VisitorPreferences, request: HttpServletRequest): String {
        val user = userRestController.getAuthenticatedUser(request)
        if (user.username == visitorPreferences.visitor.username) {
            visitorPreferencesRepository.save(visitorPreferences)
            return "success"
        }
        throw UsernameNotFoundException("No se puede actualizar el usuario  ${visitorPreferences.visitor.username} ")
    }

    @PostMapping("/visitor/get-user")
    fun testUser(request: HttpServletRequest): Visitor {
        val user = userRestController.getAuthenticatedUser(request)
        return visitorRepository.findVisitorByUsername(user.username).orElseThrow{UsernameNotFoundException("User not found!")}
    }


}
