package com.dwelling.app.elasticsearch

import com.dwelling.app.domain.Property
import com.dwelling.app.dto.FilterDto
import com.dwelling.app.dto.FilterType
import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.services.SearchService
import io.searchbox.client.JestClient
import io.searchbox.core.Search
import org.elasticsearch.common.unit.DistanceUnit
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.MatchAllQueryBuilder
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.search.sort.SortBuilders
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository

@Repository
class DwellingsSearchImpl : IDwellingsSeach {


    @Value("\${elasticsearch.distance.value}")
    private lateinit var geoDistanceValue : String

    @Value("\${elasticsearch.indexname.property}")
    private lateinit var index : String


    val logger: Logger = LoggerFactory.getLogger(DwellingsSearchImpl::class.java)

    @Autowired
    private lateinit var jestClient: JestClient

    override fun findByFilters(filters: List<FilterDto>): List<PropertyDto> {

        val searchSourceBuilder = SearchSourceBuilder()
        val queryBuilder = QueryBuilders.boolQuery()
        for (filter in filters) {
            when (filter.filterType) {
                FilterType.KEYWORD -> addKeywordFilters(filter, queryBuilder)
                FilterType.TEXT -> addTextMatchQuery(filter,queryBuilder )
                FilterType.RANGE -> addRangeFilter(filter,queryBuilder )
                FilterType.GEO -> {
                    addGeoFilter(filter,queryBuilder)
                }
                FilterType.ORDER -> print("ORDER")
            }
        }
        searchSourceBuilder.query(queryBuilder)
        logger.info(searchSourceBuilder.toString())
        val search = Search.Builder(searchSourceBuilder.toString())
                .addType(IDwellingsSeach.TYPE_PROPERTY)
                .addIndex(index)
                .build()
        val result = jestClient.execute(search)
        return if (result.isSucceeded) {
            logger.debug("[result.isSucceeded]")
            logger.debug("[properties found : ${result.total}]")
            result.getSourceAsObjectList(PropertyDto::class.java,true)
        } else {
            logger.error(result.errorMessage)
            return emptyList<PropertyDto>()
        }
    }

    private fun addRangeFilter(filter: FilterDto,queryBuilder: BoolQueryBuilder) {
        if(filter.filterRange.isNotEmpty()){
            if(filter.filterRange.size>1) {
                queryBuilder.filter(QueryBuilders.rangeQuery(filter.filterKey.key).from(filter.filterRange[0]).to(filter.filterRange[1]))
            }else{
                queryBuilder.filter(QueryBuilders.rangeQuery(filter.filterKey.key).gt(filter.filterRange[0]))
            }
        }
    }

    private fun addTextMatchQuery(filter: FilterDto,queryBuilder: BoolQueryBuilder ) {
        queryBuilder.must(QueryBuilders.matchQuery(filter.filterKey.key, filter.filterValue))
    }

    private fun addKeywordFilters(filter: FilterDto, queryBuilder: BoolQueryBuilder) {
        if (filter.filterRange.isNotEmpty()) {
            queryBuilder.must(QueryBuilders.termsQuery(filter.filterKey.key, filter.filterRange))
        } else {
            queryBuilder.must(QueryBuilders.termQuery("""${filter.filterKey.key}.keyword""",
                    filter.filterValue))
        }
    }

    private fun addGeoFilter(filter: FilterDto, queryBuilder: BoolQueryBuilder){

        if (filter.filterValue!=null)
            geoDistanceValue = filter.filterValue.toString()

        var lat = filter.filterRange[0].toString().toDouble()
        var lon = filter.filterRange[1].toString().toDouble()
        queryBuilder.filter(QueryBuilders.geoDistanceQuery(filter.filterKey.key)
                .point(lat,lon)
                .distance(geoDistanceValue,DistanceUnit.KILOMETERS))
    }



    override fun findByKeyword(keyword: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByFiltersAndKeyword(filters: List<FilterDto>, keyword: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
