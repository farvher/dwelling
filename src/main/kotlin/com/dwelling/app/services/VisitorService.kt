package com.dwelling.app.services

import com.dwelling.app.domain.Visitor
import com.dwelling.app.repository.UserRepository
import com.dwelling.app.repository.VisitorRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


/**
 * Service visitors
 * */
@Service
class VisitorService {

    val logger: Logger = LoggerFactory.getLogger(VisitorService::class.java)

    @Autowired
    private lateinit var visitorRepository: VisitorRepository

    @Autowired
    private lateinit var userRepository: UserRepository;

    /**
     * Valida si el usuario logueado puede realizar
     * la operacion sobre el id visitor proporcionado
     * */
    fun canIDoThatOperationOnMyUser(id: Long, request: ServerHttpRequest): Boolean {
        return false;
    }

    /**
     * retorna el usuario del token*/
    fun getVisitor(request: ServerHttpRequest): Mono<Visitor?> {
        return Mono.just(Visitor())
    }


    /**
     * Crea el visitor si no existe y lo devuelve
     * */
    fun getVisitor(idVisitor: Long): Mono<Visitor> {
        val user = userRepository.findById(idVisitor)
        user.map {

        }
        //   if (visitorRepository.existsById(user.id)) {
        //    val visitor = Visitor(user.id, user.username, user.firstname, user.lastname, user.email, 0, LocalDate.now(), "", "", "", user.enabled, null, null)
        //      visitorRepository.save(visitor)
        //  }
        return visitorRepository.findById(idVisitor)
    }

}
