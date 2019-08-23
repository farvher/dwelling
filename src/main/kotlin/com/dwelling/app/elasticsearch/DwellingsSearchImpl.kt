package com.dwelling.app.elasticsearch

import com.dwelling.app.dto.FilterDto
import com.dwelling.app.dto.FilterType
import com.dwelling.app.services.SearchService
import io.searchbox.client.JestClient
import io.searchbox.core.Search
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.MatchAllQueryBuilder
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class DwellingsSearchImpl : IDwellingsSeach {

    val logger: Logger = LoggerFactory.getLogger(DwellingsSearchImpl::class.java)

    @Autowired
    private lateinit var jestClient: JestClient

    override fun findByFilters(filters: List<FilterDto>): String {

        val searchSourceBuilder = SearchSourceBuilder()
        val queryBuilder = QueryBuilders.boolQuery()
        for (filter in filters) {
            when (filter.filterType) {
                FilterType.KEYWORD -> addKeywordFilters(filter, queryBuilder)
                FilterType.TEXT -> addTextMatchQuery(queryBuilder, filter)
                FilterType.RANGE -> addRangeFilter(queryBuilder, filter)
                FilterType.GEO -> {

                  //  queryBuilder.filter(QueryBuilders.geoDis)
                }
                FilterType.ORDER -> print("ORDER")
            }
        }


        filters.filter {
            it.filterType == FilterType.KEYWORD
        }.forEach {
            queryBuilder.filter(QueryBuilders.termQuery(it.filterKey, it.filterValue))
        }
        searchSourceBuilder.query(queryBuilder)

        logger.info(searchSourceBuilder.toString())
        val search = Search.Builder(searchSourceBuilder.toString()).addIndex("property").addType("property")
                .build()
        val result = jestClient.execute(search)
        if (result.isSucceeded) {
            return result.sourceAsString
        } else {
            logger.error(result.errorMessage)
            return "empty"
        }

    }

    private fun addRangeFilter(queryBuilder: BoolQueryBuilder, filter: FilterDto) {
        if(filter.filterRange.isNotEmpty()){
            if(filter.filterRange.size>1) {
                queryBuilder.must(QueryBuilders.rangeQuery(filter.filterKey).from(filter.filterRange[0]).to(filter.filterRange[1]))
            }else{
                queryBuilder.must(QueryBuilders.rangeQuery(filter.filterKey).gt(filter.filterRange[0]))
            }
        }
    }

    private fun addTextMatchQuery(queryBuilder: BoolQueryBuilder, filter: FilterDto) {
        queryBuilder.should(QueryBuilders.matchQuery(filter.filterKey, filter.filterValue))
    }

    private fun addKeywordFilters(filter: FilterDto, queryBuilder: BoolQueryBuilder) {
        if (filter.filterRange.isNotEmpty()) {
            queryBuilder.filter(QueryBuilders.termsQuery(filter.filterKey, filter.filterRange))
        } else {
            queryBuilder.filter(QueryBuilders.termQuery(filter.filterKey, filter.filterValue))
        }
    }

    override fun findByKeyword(keyword: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByFiltersAndKeyword(filters: List<FilterDto>, keyword: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
