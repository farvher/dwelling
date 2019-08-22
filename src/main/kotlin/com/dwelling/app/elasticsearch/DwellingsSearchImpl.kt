package com.dwelling.app.elasticsearch

import com.dwelling.app.dto.FilterDto
import com.dwelling.app.dto.FilterType
import com.dwelling.app.services.SearchService
import io.searchbox.client.JestClient
import io.searchbox.core.Search
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
        filters.filter {
            it.filterType == FilterType.KEYWORD
        }.forEach {
            queryBuilder.must(QueryBuilders.matchQuery(it.filterKey,it.filterValue))
        }
        searchSourceBuilder.query(queryBuilder)

        logger.info(searchSourceBuilder.toString())
        val search = Search.Builder(searchSourceBuilder.toString()).addIndex("property").addType("property")
                .build()
        val result = jestClient.execute(search)
        if(result.isSucceeded){
            return result.sourceAsString
        }else{
            logger.error(result.errorMessage)
            return "empty"
        }

    }

    override fun findByKeyword(keyword: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByFiltersAndKeyword(filters: List<FilterDto>, keyword: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
