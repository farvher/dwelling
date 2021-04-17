package com.dwelling.app.elasticsearch

import com.dwelling.app.dto.PropertyDto

interface DwellingPersist {

    fun saveProperty(propertyDto: PropertyDto)

    fun deleteProperty(id: Long)

}