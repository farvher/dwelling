package com.dwelling.app.security.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Entity
@Table(name = "USER_APP")
class User(@Id
           @Column(name = "ID")
           @GeneratedValue(strategy = GenerationType.IDENTITY)
           var id: Long,

           @Column(name = "USERNAME", length = 50, unique = true)
           @NotNull
           @Size(min = 4, max = 50)
           var username: String,

           @Column(name = "PASSWORD", length = 100)
           @NotNull
           @Size(min = 4, max = 100)
           @JsonIgnore
           var password: String,

           @Column(name = "FIRSTNAME", length = 50)
           @NotNull
           @Size(min = 4, max = 50)
           var firstname: String,

           @Column(name = "LASTNAME", length = 50)
           @NotNull
           @Size(min = 4, max = 50)
           var lastname: String,

           @Column(name = "EMAIL", length = 50)
           @NotNull
           @Size(min = 4, max = 50)
           var email: String,

           @Column(name = "ENABLED")
           @NotNull
           var enabled: Boolean,

           @Column(name = "LASTPASSWORDRESETDATE")
           @Temporal(TemporalType.TIMESTAMP)
           @NotNull
           var lastPasswordResetDate: Date,

           @ManyToMany(fetch = FetchType.EAGER)
           @JoinTable(name = "USER_AUTHORITY", joinColumns = [JoinColumn(name = "USER_ID", referencedColumnName = "ID")], inverseJoinColumns = [JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")])
           var authorities: List<Authority>)
