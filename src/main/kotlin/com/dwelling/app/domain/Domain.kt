package com.dwelling.app.domain


import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate


@Table
data class Visitor(
    @Id val id: Long = -1,
    var username: String? = null,
    var name: String? = null,
    var lastname: String? = null,
    var email: String? = null,
    var age: Int? = null,
    var creationDate: LocalDate? = null,
    var phone: String? = null,
    var cellPhone: String? = null,
    var urlSite: String? = null,
    var enable: Boolean = true
)


@Table
data class RealState(
    @Id val id: Long,
    var name: String,
)

@Table
data class Builder(
    @Id val id: Long,
    var name: String,

    )

@Table
data class Contact(
    @Id val id: Long,
    var contactName: String,
    var contactEmail: String? = null,
    var contactWebSite: String? = null,
    var address1: String,
    var address2: String? = null,
    var address3: String? = null,
    var phone1: String,
    var phone2: String? = null,
    var phone3: String? = null
)


@Table
data class Image(
    @Id val id: Long,
    var title: String,
    var url: String,
    var available: Boolean
)
