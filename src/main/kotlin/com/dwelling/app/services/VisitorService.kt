package com.dwelling.app.services

import com.dwelling.app.domain.Visitor
import com.dwelling.app.repository.VisitorPreferencesRepository
import com.dwelling.app.repository.VisitorRepository
import com.dwelling.app.security.JwtUser
import com.dwelling.app.security.controller.UserRestController
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest


/**
 * Service visitors
 * */
@Service
class VisitorService {

    val logger: Logger = LoggerFactory.getLogger(VisitorService::class.java)

    @Autowired
    private lateinit var visitorRepository: VisitorRepository
    @Autowired
    private lateinit var visitorPreferencesRepository: VisitorPreferencesRepository
    @Autowired
    private lateinit var userRestController: UserRestController

    /**
     * Valida si el usuario logueado puede realizar
     * la operacion sobre el id visitor proporcionado
     * */
    fun canIDoThatOperationOnMyUser(id: Long, request: HttpServletRequest): Boolean {
        var user = userRestController.getAuthenticatedUser(request)
        return user.id == id;
    }

    /**
     * retorna el usuario del token*/
    fun getVisitor(request: HttpServletRequest): JwtUser {
        return userRestController.getAuthenticatedUser(request)
    }

    fun getVisitor(idVisitor: Long): Visitor {
        return visitorRepository.findById(idVisitor).orElseThrow { UsernameNotFoundException("Visitor not found") }
    }

}
