package com.dwelling.app.domain

import jdk.nashorn.internal.runtime.PropertyMap
import org.hibernate.annotations.Cascade
import sun.security.util.Length
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
                   var role: List<Role>? = null,
                   @OneToOne(cascade = [CascadeType.ALL])
                   var builder: Builder? = null,
                   @OneToOne(cascade = [CascadeType.ALL])
                   var realState: RealState? = null
)

@Entity
data class VisitorPreferences(@Id @GeneratedValue val id : Long = -1,
                              @OneToOne(cascade = [CascadeType.ALL])
                              var visitor: Visitor,
                              var incomeValue: Long,
                              var outcomeValue: Long,
                              @OneToMany(mappedBy = "visitorPreferences", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
                              @OrderColumn(name = "pos")
                              var visitorLocations: List<VisitorLocation>? = null,
                              var partners : Long
                              )

@Entity
data class VisitorLocation(@Id @GeneratedValue val id : Long = -1,
                           @OneToOne
                           var visitor: Visitor,
                           var locationType:String,
                           var locationTitle: String,
                           var latitude: Long,
                           var length: Long,
                           @ManyToOne
                           @JoinColumn(name = "visitor_location_id")
                           var visitorPreferences: VisitorPreferences
                           )



@Entity
data class Property(@Id @GeneratedValue val id: Long = -1,
                    @OneToOne(cascade = [CascadeType.ALL])
                    var visitor:Visitor,
                    @OneToOne(cascade = [CascadeType.ALL])
                    var propertyType: PropertyType,
                    var title: String,
                    @OneToOne(cascade = [CascadeType.ALL])
                    var neighborhood: Neighborhood,
                    var description: String,
                    @OneToMany(mappedBy = "property", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
                    @OrderColumn(name = "pos")
                    var images: List<Image>,
                    @OneToOne(cascade = [CascadeType.ALL])
                    var contact: Contact,
                    var antiquitiy: Int,
                    var rentPrice: Double?,
                    var sellPrince: Double?,
                    var area: Int,
                    var rooms: Int,
                    var stratum: Int,
                    var buildTime: Int,
                    var bathroom: Int,
                    @OneToMany(mappedBy = "property", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
                    @OrderColumn(name = "pos")
                    var aditional: List<Additional>? = null,
                    @OneToOne(cascade = [CascadeType.ALL])
                    var builder: Builder? = null,
                    @OneToOne(cascade = [CascadeType.ALL])
                    var realState: RealState? = null,
                    var latitude: Long = 0,
                    var length: Int = 0
){
    override fun hashCode(): Int = super.hashCode()
    override fun equals(other: Any?): Boolean = super.equals(other)
}

@Entity
data class RealState(@Id @GeneratedValue val id: Long,
                     var name: String,
                     @OneToOne(cascade = [CascadeType.ALL])
                     var contact: Contact,
                     @OneToOne(cascade = [CascadeType.ALL])
                     var visitor: Visitor)

@Entity
data class Builder(@Id @GeneratedValue val id: Long,
                   var name: String,
                   @OneToOne(cascade = [CascadeType.ALL])
                   var contact: Contact,
                   @OneToOne(cascade = [CascadeType.ALL])
                   var visitor: Visitor)

@Entity
data class Contact(@Id @GeneratedValue val id: Long,
                   @OneToOne(cascade = [CascadeType.ALL])
                   var builder: Builder?,
                   @OneToOne(cascade = [CascadeType.ALL])
                   var realState: RealState?)

@Entity
data class Additional(@Id @GeneratedValue(strategy =  GenerationType.SEQUENCE) val id: Long,
                     var name: String,
                     var value: String,
                     @ManyToOne(cascade = [CascadeType.ALL])
                     @JoinColumn(name = "property_id")
                     val property: Property)

@Entity
data class Role(@Id @GeneratedValue val id: Long,
                var name: String,
                @ManyToOne(cascade = [CascadeType.ALL])
                @JoinColumn(name = "visitor_id")
                var visitor: Visitor
)

@Entity
data class PropertyType(@Id @GeneratedValue(strategy =  GenerationType.SEQUENCE) val id: Long,
                        val name: String)

@Entity
data class Image(@Id @GeneratedValue val id: Long,
                 var title: String,
                 var url: String,
                 var available: Boolean ,
                 @ManyToOne(cascade = [CascadeType.ALL])
                 @JoinColumn(name = "property_id")
                 val property: Property? =null)

@Entity
data class City(@Id @GeneratedValue(strategy =  GenerationType.SEQUENCE) val id: Long,
                var name: String,
                @OneToOne(cascade = [CascadeType.ALL])
                var country: Country)

@Entity
data class Country(@Id @GeneratedValue val id:Long,
                   var name: String)

@Entity
data class Zone(@Id @GeneratedValue(strategy =  GenerationType.SEQUENCE)  val id: Long,
                var name: String,
                @OneToOne(cascade = [CascadeType.ALL])
                var city: City)

@Entity
data class Neighborhood(@Id @GeneratedValue(strategy =  GenerationType.SEQUENCE) val id: Long,
                        var name: String,
                        @OneToOne(cascade = [CascadeType.ALL])
                        var zone: Zone)



@Entity
data class Auditoria(@Id @GeneratedValue(strategy =  GenerationType.SEQUENCE) val id: Long,
                 @OneToOne(cascade = [CascadeType.ALL])
                 var property: Property,
                 var operation: String,
                 var dateModification: LocalDate,
                 @OneToOne(cascade = [CascadeType.ALL])
                 var visitor: Visitor
                 )
