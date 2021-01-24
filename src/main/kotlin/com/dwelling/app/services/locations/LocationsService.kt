package com.dwelling.app.services.locations

import com.dwelling.app.repository.LocationsRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * Location service extracted from database and a json file
 * @author fsanmiguel
 * */
@Service
class LocationsService(locationsRepository: LocationsRepository) {

    val logger: Logger = LoggerFactory.getLogger(LocationsService::class.java)


}
