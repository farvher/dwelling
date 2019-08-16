package com.dwelling.app.services

import com.dwelling.app.domain.VisitorPreferences
import com.dwelling.app.dto.VisitorPreferencesDto
import com.dwelling.app.repository.VisitorPreferencesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class PreferencesService {

    @Autowired
    private lateinit var visitorPreferencesRepository: VisitorPreferencesRepository

    fun savePreferences(visitorPreferences: VisitorPreferences){
        visitorPreferencesRepository.save(visitorPreferences)
    }


}
