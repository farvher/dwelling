package com.dwelling.app.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("USER_APP")
class User(
    @Id
    @Column("ID")
    var id: Long,
    @Column("USERNAME")
    var username: String,
    @Column("PASSWORD")
    @JsonIgnore
    var password: String,
    @Column("FIRSTNAME")
    var firstname: String,
    @Column("LASTNAME")
    var lastname: String,
    @Column("EMAIL")
    var email: String,
    @Column("ENABLED")
    var enabled: Boolean,
    @Column("LASTPASSWORDRESETDATE")
    var lastPasswordResetDate: LocalDateTime,
)

@Table("AUTHORITY")
class Authority(
    @Id
    @Column("ID")
    var id: Long,

    @Column("NAME")
    var name: String
)

enum class AuthorityName {
    ROLE_USER, ROLE_ADMIN

}


