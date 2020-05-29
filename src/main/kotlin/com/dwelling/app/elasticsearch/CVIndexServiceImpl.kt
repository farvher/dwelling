package com.dwelling.app.elasticsearch

import com.dwelling.app.domain.CV
import com.dwelling.app.domain.Property
import com.dwelling.app.exceptions.SearchServiceException
import io.searchbox.client.JestClient
import io.searchbox.core.Search
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class CVIndexServiceImpl : ICVIndexService {

    @Autowired
    private lateinit var elasticIndexService: ElasticIndexService<CV>

    val logger: Logger = LoggerFactory.getLogger(CVIndexServiceImpl::class.java)


    @Autowired
    private lateinit var jestClient: JestClient

    @Value("\${elasticsearch.indexname.cvdata}")
    private lateinit var index : String

    @Value("\${elasticsearch.indexname.cvdata}")
    private lateinit var type : String

    @PostConstruct
    fun init(){
        elasticIndexService.createIndexIfNotExists(index)
    }

    override fun getCVById(id: Long): CV {
        val searchSourceBuilder = SearchSourceBuilder()
        searchSourceBuilder.query(QueryBuilders.termQuery("id",id))
        searchSourceBuilder.from(0)
        logger.info(searchSourceBuilder.toString())
        val search = Search.Builder(searchSourceBuilder.toString())
                .addIndex(index)
                .addType(type)
                .build()
        val result = jestClient.execute(search)
        if (result.isSucceeded) {
            logger.info(result.sourceAsString)

        } else {
            throw SearchServiceException(result.errorMessage)
        }

        return result.getSourceAsObject(CV::class.java, false)
    }

    override fun saveCV(cv: CV) {
        elasticIndexService.createElement(cv,index,type)
    }

    override fun updateCV(cv: CV) {
        TODO("Not yet implemented")
    }

    override fun deleteCV(id: Long, cvId: Long) {
        TODO("Not yet implemented")
    }
}
