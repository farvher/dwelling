package com.dwelling.app.controllers

import com.dwelling.app.domain.VisitorPreferences
import com.dwelling.app.dto.VisitorPreferencesDto
import com.dwelling.app.services.LocationsService
import com.dwelling.app.services.PreferencesService
import com.dwelling.app.services.PropertyService
import com.dwelling.app.services.VisitorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import javax.servlet.http.HttpServletRequest

/**
 * RestController handle user preferences
 * @author fsanmiguel
 * */
@RestController
class PreferencesController {


    @Autowired
    private lateinit var preferencesService: PreferencesService
    @Autowired
    private lateinit var locationService: LocationsService
    @Autowired
    private lateinit var propertyService: PropertyService
    @Autowired
    private lateinit var visitorService: VisitorService




    @PostMapping("/preferences/save")
    fun savePreferences(@RequestBody preferences: VisitorPreferencesDto, request: HttpServletRequest)
    : Mono<ResponseEntity<String>>{
        if (visitorService.canIDoThatOperationOnMyUser(preferences.idVisitor, request)) {
            var visitor = visitorService.getVisitor(idVisitor = preferences.idVisitor )

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
            return Mono.fromCallable { ResponseEntity.ok().body("Ok")}

        }
        return Mono.fromCallable { ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No Authorized!")}

    }

    @GetMapping("/preferences/get/{idVisitor}")
    fun getPreferences(@PathVariable idVisitor: Long, request: HttpServletRequest)
    : Mono<ResponseEntity<VisitorPreferencesDto?>>{
        var visitorPreferencesDto: VisitorPreferencesDto? = null
        if (visitorService.canIDoThatOperationOnMyUser(idVisitor, request)) {

            TODO("obtener preferencias del visitor")
        }
        return Mono.fromCallable { ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(visitorPreferencesDto)}
    }


}
