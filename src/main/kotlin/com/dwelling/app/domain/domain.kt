package com.dwelling.app.domain

import java.time.LocalDate
import javax.persistence.*

@Entity
data class Visitor(@Id @GeneratedValue val id: Long = -1,
                   var username: String? = null,
                   var name: String? = null,
                   var lastname: String? = null,
                   var email: String? = null,
                   var age: Int? = null,
                   var creationDate: LocalDate? = null,
                   var phone: String? = null,
                   var cellPhone: String? = null,
                   var urlSite: String? = null,
                   var enable: Boolean = true,
                   var password: String? = null,
                   @OneToMany(mappedBy = "visitor", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
                   @OrderColumn(name = "pos")
                   var role: List<Role>? = null,
                   @OneToOne
                   var builder: Builder? = null,
                   @OneToOne
                   var realState: RealState? = null
)


@Entity
data class Property(@Id @GeneratedValue val id: Long = -1,
                    @OneToOne
                    var city: City,
                    @OneToOne
                    var propertyType: PropertyType,
                    var title: String,
                    @OneToOne
                    var neighborhood: Neighborhood,
                    var description: String,
                    @OneToOne
                    var zone: Zone,
                    var images: Array<Image>,
                    @OneToOne
                    var contact: Contact,
                    var antiquitiy: Int,
                    var rentPrice: Double,
                    var sellPrince: Double,
                    var area: Int,
                    var rooms: Int,
                    var stratum: Int,
                    var buildTime: Int,
                    var bathroom: Int,
                    @OneToMany(mappedBy = "property", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
                    @OrderColumn(name = "pos")
                    var aditional: Array<Additional>,
                    @OneToOne
                    var builder: Builder,
                    @OneToOne
                    var realState: RealState,
                    var latitude: Long,
                    var length: Int
){
    override fun hashCode(): Int = super.hashCode()
    override fun equals(other: Any?): Boolean = super.equals(other)
}

@Entity
data class RealState(@Id @GeneratedValue val id: Long,
                     var name: String,
                     @OneToOne
                     var contact: Contact,
                     @OneToOne
                     var visitor: Visitor)

@Entity
data class Builder(@Id @GeneratedValue val id: Long,
                   var name: String,
                   @OneToOne
                   var contact: Contact,
                   @OneToOne
                   var visitor: Visitor)

@Entity
data class Contact(@Id @GeneratedValue val id: Long,
                   @OneToOne
                   var builder: Builder,
                   @OneToOne
                   var realState: RealState)

@Entity
data class Additional(@Id @GeneratedValue val id: Long,
                     var name: String,
                     var value: String,
                     @ManyToOne
                     @JoinColumn(name = "property_id")
                     val property: Property)

@Entity
data class Role(@Id @GeneratedValue val id: Long,
                var name: String,
                @ManyToOne
                @JoinColumn(name = "role_id")
                var visitor: Visitor
)

@Entity
data class PropertyType(@Id @GeneratedValue val id: Long,
                        val name: String,
                        @OneToOne
                        val propertyType: PropertyType)

@Entity
data class Image(@Id @GeneratedValue val id: Long,
                 var title: String,
                 var url: String,
                 var available: Boolean)

@Entity
data class City(@Id @GeneratedValue val id: Long,
                val name: String,
                var country: String)

@Entity
data class Zone(@Id @GeneratedValue val id: Long,
                var name: String,
                @OneToOne
                var city: City)

@Entity
data class Neighborhood(@Id @GeneratedValue val id: Long,
                        var name: String,
                        @OneToOne
                        var zone: Zone)



