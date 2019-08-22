package com.dwelling.app.elasticsearch

import com.dwelling.app.dto.FilterDto

interface IDwellingsSeach {

    fun findByFilters(filters:List<FilterDto>) : String

    fun findByKeyword(keyword : String) : String

    fun findByFiltersAndKeyword(filters: List<FilterDto>, keyword: String) : String



}
