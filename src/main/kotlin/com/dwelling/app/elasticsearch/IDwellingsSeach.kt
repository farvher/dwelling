package com.dwelling.app.elasticsearch

interface IDwellingsSeach {

    fun findByFilters(filters:Map<String, Any>) : String

    fun findByKeyword(keyword : String) : String

    fun findByFiltersAndKeyword(filters: Map<String, Any>, keyword: String) : String



}
