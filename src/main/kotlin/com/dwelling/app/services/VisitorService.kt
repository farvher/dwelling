package com.dwelling.app.services

import com.dwelling.app.domain.Visitor
import com.dwelling.app.repository.VisitorPreferencesRepository
import com.dwelling.app.repository.VisitorRepository
import com.dwelling.app.security.controller.UserRestController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest


/**
 * Service visitors
 * */
@Service
class VisitorService {


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
        return user.id == id ;
    }

    fun getVisitor(idVisitor : Long) : Visitor {
        return visitorRepository.findById(idVisitor).orElseThrow{UsernameNotFoundException("Visitor not found")}
    }

}
