package com.dwelling.app.controllers

import com.dwelling.app.domain.VisitorPreferences
import com.dwelling.app.dto.VisitorPreferencesDto
import com.dwelling.app.services.locations.LocationsService
import com.dwelling.app.services.PreferencesService
import com.dwelling.app.services.property.PropertyService
import com.dwelling.app.services.VisitorService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.servlet.http.HttpServletRequest

/**
 * RestController handle user preferences
 * @author fsanmiguel
 * */
@RestController()
class PreferencesController {

    val logger: Logger = LoggerFactory.getLogger(PreferencesController::class.java)

    @Autowired
    private lateinit var preferencesService: PreferencesService
    @Autowired
    private lateinit var locationService: LocationsService
    @Autowired
    private lateinit var propertyService: PropertyService
    @Autowired
    private lateinit var visitorService: VisitorService


    @PostMapping("/preferences/save")
    fun savePreferences(@RequestBody preferences: VisitorPreferencesDto, request: HttpServletRequest) {
            var visitorjWt = visitorService.getVisitor(request)
            var visitor = visitorService.getVisitor(visitorjWt.id!!)

            var visitorPreferences = VisitorPreferences(
                    id = preferences.id,
                    visitor = visitor,
                    nearToMe = preferences.nearToMe,
                    favoritePropertyType = preferences.favoritePropertyType,
                    outcomeValue = preferences.outcomeValue!!,
                    incomeValue = preferences.incomeValue!!,
                    visitorLocations = preferences.visitorLocations,
                    withPets = preferences.withPets,
                    withParkings = preferences.withParkings,
                    withKids = preferences.withKids,
                    countPartners = preferences.countPartners!!,
                    favoriteZone = locationService.findZoneByName(preferences.favoriteZone!!),
                    favoriteLocation = preferences.favoriteLocation!!,
                    favoriteCountry = locationService.findCountryByName(preferences.favoriteCountry!!),
                    favoriteCity = locationService.findCityByName(preferences.favoriteCity!!),
                    favoriteBusinessType = preferences.favoriteBusinessType
            )

            preferencesService.savePreferences(visitorPreferences)

    }

    @GetMapping("/preferences")
    fun getPreferences( request: HttpServletRequest)
            : Mono<VisitorPreferencesDto> {
        var visitorPreferencesDto: VisitorPreferencesDto? = null
        val visitor = visitorService.getVisitor(request)

        TODO("obtener preferencias del visitor")

    }


}
