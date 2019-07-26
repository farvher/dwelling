package com.dwelling.app.elasticsearch

import io.searchbox.client.JestClient
import org.elasticsearch.index.query.MatchAllQueryBuilder
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class DwellingsSerachImpl : IDwellingsSeach {

    @Autowired
    private lateinit var  jestClient: JestClient

    override fun findByFilters(filters: Map<String, Any>): String {

        val size = filters.get("SIZE")



        //build query by filte

       // val searchSourceBuilder = SearchSourceBuilder(QueryBuilders.boolQuery().must)
       // searchSourceBuilder.size()


    }

    override fun findByKeyword(keyword: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByFiltersAndKeyword(filters: Map<String, Any>, keyword: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
