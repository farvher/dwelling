package com.dwelling.app.elasticsearch

import com.dwelling.app.domain.Property
import com.dwelling.app.dto.FilterDto
import com.dwelling.app.dto.PropertyDto

interface IDwellingsSeach {

    fun findByFilters(filters: List<FilterDto>): List<PropertyDto>

    fun findByKeyword(keyword: String): String

    fun findByFiltersAndKeyword(filters: List<FilterDto>, keyword: String): String

    companion object {
        const val INDEX_PROPERTY = "property"
        const val TYPE_PROPERTY = "property"
    }

}
