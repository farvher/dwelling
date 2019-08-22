package com.dwelling.app.dto

/**
 * dto for searchlybox queries
 * */
class FilterDto(
        val filterKey: String,
        val filterValue :Any,
        val filterRange: List<Any> = emptyList(),
        val filterType: FilterType = FilterType.KEYWORD)


enum class FilterType {
    RANGE, KEYWORD, TEXT,GEO
}
