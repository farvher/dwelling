package com.dwelling.app.controllers

import com.dwelling.app.domain.Visitor
import com.dwelling.app.repository.VisitorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class VisitorController {

    @Autowired
    private lateinit var visitorRepository: VisitorRepository

    @PostMapping(path = ["/create-visitor"])
    fun createVisitor(email: String?,
                      name: String?,
                      username: String?,
                      lastname: String?,
                      age: Int?,
                      cellphone: String?,
                      phone: String?,
                      urlSite: String?,
                      password: String?
    ): Visitor? {
        val visitor = Visitor(email = email,
                name = name,
                id = -1,
                username = username,
                lastname = lastname,
                age = age, builder = null,
                cellPhone = cellphone,
                creationDate = null,
                phone = phone,
                urlSite = urlSite,
                enable = false,
                password = password,
                realState = null,
                role = null)
        if (!visitorRepository.findVisitorByUsername(username).isPresent) {
            visitorRepository.save(visitor)
        } else {
            throw  IllegalStateException("Username ${username} already exist!!")
        }


        return visitorRepository.findVisitorByUsername(username).orElse(visitor)
    }

    @GetMapping(path = ["/get-visitor/{id}"])
    fun getVisitor(@PathVariable id: Long?): Visitor? {
        return visitorRepository.findByIdOrNull(id)

    }


}
