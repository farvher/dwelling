package com.dwelling.app.elasticsearch

import com.dwelling.app.dto.FilterDto
import com.dwelling.app.dto.FilterType
import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.repository.ElasticPropertyRepository
import com.dwelling.app.services.SearchService
import io.searchbox.client.JestClient
import io.searchbox.core.Search
import org.elasticsearch.common.unit.DistanceUnit
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class DwellingSearchImpl(
    private val elasticPropertyRepository: ElasticPropertyRepository,
    private val jestClient: JestClient,
    private val searchService: SearchService<PropertyDto>

) : DwellingSearch {

    val logger: Logger = LoggerFactory.getLogger(DwellingSearchImpl::class.java)

    @Value("\${elasticsearch.distance.value}")
    private lateinit var geoDistanceValue: String

    @Value("\${elasticsearch.indexname.property}")
    private lateinit var index: String


    override fun findByFilters(filters: List<FilterDto>): Flux<PropertyDto> {

        val searchSourceBuilder = SearchSourceBuilder()
        val queryBuilder = QueryBuilders.boolQuery()
        for (filter in filters) {
            when (filter.filterType) {
                FilterType.KEYWORD -> addKeywordFilters(filter, queryBuilder)
                FilterType.TEXT -> addTextMatchQuery(filter, queryBuilder)
                FilterType.RANGE -> addRangeFilter(filter, queryBuilder)
                FilterType.GEO -> {
                    addGeoFilter(filter, queryBuilder)
                }
                FilterType.ORDER -> print("ORDER")
            }
        }
        searchSourceBuilder.query(queryBuilder)
        logger.info(searchSourceBuilder.toString())
        val search = Search.Builder(searchSourceBuilder.toString())
            .addType("property")//TODO REMOVE
            .addIndex(index)
            .build()
        val result = jestClient.execute(search)
        return if (result.isSucceeded) {
            Flux.fromIterable(result.getSourceAsObjectList(PropertyDto::class.java, true))
        } else {
            logger.error(result.errorMessage)
            return Flux.empty()
        }
    }

    private fun addRangeFilter(filter: FilterDto, queryBuilder: BoolQueryBuilder) {
        if (filter.filterRange.isNotEmpty()) {
            if (filter.filterRange.size > 1) {
                queryBuilder.must(
                    QueryBuilders.rangeQuery(filter.filterKey.key).from(filter.filterRange[0]).to(filter.filterRange[1])
                )
            } else {
                queryBuilder.must(QueryBuilders.rangeQuery(filter.filterKey.key).gt(filter.filterRange[0]))
            }
        }
    }

    private fun addTextMatchQuery(filter: FilterDto, queryBuilder: BoolQueryBuilder) {
        queryBuilder.must(QueryBuilders.matchQuery(filter.filterKey.key, filter.filterValue))
    }

    private fun addKeywordFilters(filter: FilterDto, queryBuilder: BoolQueryBuilder) {
        if (filter.filterRange.isNotEmpty()) {
            queryBuilder.filter(QueryBuilders.termsQuery(filter.filterKey.key, filter.filterRange))
        } else {
            queryBuilder.filter(QueryBuilders.termQuery(filter.filterKey.key, filter.filterValue))
        }
    }

    private fun addGeoFilter(filter: FilterDto, queryBuilder: BoolQueryBuilder) {
        var lat = filter.filterRange[0].toString().toDouble()
        var lon = filter.filterRange[1].toString().toDouble()
        queryBuilder.filter(
            QueryBuilders.geoDistanceQuery(filter.filterKey.key)
                .point(lat, lon)
                .distance(geoDistanceValue, DistanceUnit.KILOMETERS)
        )
    }


    override fun findByKeyword(keyword: String): Flux<PropertyDto> {
        return elasticPropertyRepository.findAll()
     //   return Flux.fromIterable(searchService.searchByQueryString(keyword))
    }

    override fun findByFiltersAndKeyword(filters: List<FilterDto>, keyword: String): Flux<PropertyDto> {
        return elasticPropertyRepository.findAll()
    }

    override fun findById(id: Long): Mono<PropertyDto> {
        return elasticPropertyRepository.findById(id)
    }


}
