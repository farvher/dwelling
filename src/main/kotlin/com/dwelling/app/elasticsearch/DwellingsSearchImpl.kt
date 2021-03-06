package com.dwelling.app.elasticsearch

import com.dwelling.app.domain.Property
import com.dwelling.app.dto.FilterDto
import com.dwelling.app.dto.FilterType
import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.services.SearchService
import io.searchbox.client.JestClient
import io.searchbox.core.Search
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.MatchAllQueryBuilder
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.search.sort.SortBuilders
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class DwellingsSearchImpl : IDwellingsSeach {

    val logger: Logger = LoggerFactory.getLogger(DwellingsSearchImpl::class.java)

    @Autowired
    private lateinit var jestClient: JestClient

    override fun findByFilters(filters: List<FilterDto>): List<Property> {

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
        searchSourceBuilder.query(queryBuilder)
        logger.info(searchSourceBuilder.toString())
        val search = Search.Builder(searchSourceBuilder.toString())
                .addType(IDwellingsSeach.TYPE_PROPERTY)
                .addIndex(IDwellingsSeach.INDEX_PROPERTY)
                .build()
        val result = jestClient.execute(search)
        return if (result.isSucceeded) {
            result.getSourceAsObjectList(Property::class.java,true)
        } else {
            logger.error(result.errorMessage)
            return emptyList<Property>()
        }
    }

    private fun addRangeFilter(queryBuilder: BoolQueryBuilder, filter: FilterDto) {
        if(filter.filterRange.isNotEmpty()){
            if(filter.filterRange.size>1) {
                queryBuilder.must(QueryBuilders.rangeQuery(filter.filterKey.key).from(filter.filterRange[0]).to(filter.filterRange[1]))
            }else{
                queryBuilder.must(QueryBuilders.rangeQuery(filter.filterKey.key).gt(filter.filterRange[0]))
            }
        }
    }

    private fun addTextMatchQuery(queryBuilder: BoolQueryBuilder, filter: FilterDto) {
        queryBuilder.must(QueryBuilders.matchQuery(filter.filterKey.key, filter.filterValue))
    }

    private fun addKeywordFilters(filter: FilterDto, queryBuilder: BoolQueryBuilder) {
        if (filter.filterRange.isNotEmpty()) {
            queryBuilder.filter(QueryBuilders.termsQuery(filter.filterKey.key, filter.filterRange))
        } else {
            queryBuilder.filter(QueryBuilders.termQuery(filter.filterKey.key, filter.filterValue))
        }
    }

    override fun findByKeyword(keyword: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByFiltersAndKeyword(filters: List<FilterDto>, keyword: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
