package com.dwelling.app.domain


import com.fasterxml.jackson.annotation.*
import com.google.gson.annotations.Expose
import java.time.LocalDate
import javax.persistence.*
import kotlin.jvm.Transient


@Entity
data class Visitor(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = -1,
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
                   @OneToOne(cascade = [CascadeType.ALL])
                   var builder: Builder? = null,
                   @OneToOne(cascade = [CascadeType.ALL])
                   var realState: RealState? = null
)
@Entity
data class  VisitorFavorite(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = -1,
                             @OneToOne(cascade = [CascadeType.REFRESH],fetch = FetchType.LAZY)
                             var property: Property,
                             @OneToOne(cascade = [CascadeType.REFRESH],fetch = FetchType.LAZY)
                             var visitor: Visitor
                             )

@Entity
data class VisitorPreferences(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = -1,
                              var favoritePropertyType: PropertyTypeEnum,
                              var favoriteBusinessType: BusinessTypeEnum,
                              @OneToOne(cascade = [CascadeType.REFRESH],fetch = FetchType.LAZY)
                              var favoriteCity : City,
                              @OneToOne(cascade = [CascadeType.REFRESH],fetch = FetchType.LAZY)
                              var favoriteZone: Zone,
                              @OneToOne(cascade = [CascadeType.REFRESH],fetch = FetchType.LAZY)
                              var favoriteCountry: Country,
                              var nearToMe : Boolean,
                              var favoriteLocation : String,
                              var incomeValue: Double,
                              var outcomeValue: Double,
                              @OneToOne(cascade = [CascadeType.REFRESH])
                              var visitor: Visitor,
                              var withParkings : Boolean,
                              var withPets : Boolean,
                              var withKids : Boolean,
                              @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
                              @JoinColumn(name = "locations_visitor_fk")
                              var visitorLocations: List<VisitorLocation>? = null,
                              var countPartners: Long
)

@Entity
data class VisitorLocation(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = -1,
                           var locationType: String,
                           var locationTitle: String,
                           var latitude: Double,
                           var longitude: Double
)


@Entity
data class Property(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = -1,
                    var propertyType: PropertyTypeEnum,
                    var businessType : BusinessTypeEnum,
                    var title: String,
                    @OneToOne(cascade = [CascadeType.REFRESH])
                    var neighborhood: Neighborhood,
                    var description: String,
                    var imageCount: Int?,
                    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
                    @JoinColumn(name = "img_fk")
                    var images: List<Image>?,
                    var antiquitiy: String,
                    var rentPrice: Double?,
                    var sellPrince: Double?,
                    var area: Int,
                    var areaUnit: String = "m2",
                    var rooms: Int,
                    var stratum: Int,
                    var buildTime: Int,
                    var bathroom: Int,
                    var parking: Int,
                    var admon: Double,
                    var floor: Int? = null,
                    @ManyToMany(cascade = [CascadeType.ALL], targetEntity = Additional::class)
                    @JoinTable(name = "ADDITIONAL_PROPERTY", joinColumns = [JoinColumn(name = "property_id")]
                            , inverseJoinColumns = [JoinColumn(name = "additional_id")])
                    var additional: List<Additional>? = null,
                    @OneToOne(cascade = [CascadeType.ALL])
                    var visitor: Visitor,
                    @OneToOne(cascade = [CascadeType.ALL])
                    var location : Location

) {
    override fun hashCode(): Int = super.hashCode()
    override fun equals(other: Any?): Boolean = super.equals(other)
    override fun toString(): String {
        return "Property [${this.id}]"
    }
}

@Entity
data class Location(@Id @GeneratedValue(strategy = GenerationType.IDENTITY)@JsonIgnore var id : Long = -1,
                    var lat : Double,
                    var lon: Double)

@Entity
data class RealState(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
                     var name: String,
                     @OneToOne(cascade = [CascadeType.ALL])
                     var contact: Contact)

@Entity
data class Builder(@Id @GeneratedValue val id: Long,
                   var name: String,
                   @OneToOne(cascade = [CascadeType.ALL])
                   var contact: Contact)

@Entity
data class Contact(@Id @GeneratedValue val id: Long,
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

@Entity
data class Additional(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
                      var value: String,
                      @ManyToMany(mappedBy = "additional", cascade = [CascadeType.ALL], targetEntity = Property::class)
                      @JsonIgnore
                      @Transient
                      var  property: List<Property>?=null)


@Entity
data class Image(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
                 var title: String,
                 var url: String,
                 var available: Boolean)

@Entity
data class City(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
                var name: String,
                @OneToOne(cascade = [CascadeType.ALL])
                var country: Country)

@Entity
data class Country(@Id @GeneratedValue val id: Long,
                   var name: String)

@Entity
data class Zone(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
                var name: String,
                @OneToOne(cascade = [CascadeType.ALL])
                var city: City)

@Entity
data class Neighborhood(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
                        var name: String,
                        @OneToOne(cascade = [CascadeType.ALL])
                        var zone: Zone)


@Entity
data class Auditoria(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
                     @OneToOne(cascade = [CascadeType.ALL])
                     var property: Property,
                     var operation: String,
                     var dateModification: LocalDate,
                     @OneToOne(cascade = [CascadeType.ALL])
                     var visitor: Visitor
)
