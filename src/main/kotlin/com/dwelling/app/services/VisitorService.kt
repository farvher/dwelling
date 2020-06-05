package com.dwelling.app.services

import com.dwelling.app.domain.Visitor
import com.dwelling.app.repository.VisitorPreferencesRepository
import com.dwelling.app.repository.VisitorRepository
import com.dwelling.app.security.JwtUser
import com.dwelling.app.security.controller.UserRestController
import com.dwelling.app.security.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDate
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

    @Autowired
    private lateinit var userRepository: UserRepository;

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


    /**
     * Crea el visitor si no existe y lo devuelve
     * */
    fun getVisitor(idVisitor: Long): Visitor {
        val user = userRepository.findById(idVisitor).orElseThrow { UsernameNotFoundException("User not found") }
        if (!visitorRepository.existsById(user.id)) {
            val visitor = Visitor(user.id, user.username, user.firstname, user.lastname, user.email, 0, LocalDate.now(), "", "", "", user.enabled, null, null)
            visitorRepository.save(visitor)
        }
        return visitorRepository.findById(idVisitor).orElseThrow { UsernameNotFoundException("Visitor not found") }
    }

}
