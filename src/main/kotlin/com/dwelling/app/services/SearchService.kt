package com.dwelling.app.services


import com.dwelling.app.domain.Location
import com.dwelling.app.domain.Property
import com.dwelling.app.dto.PropertyDto
import com.dwelling.app.exceptions.SearchServiceException
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import io.searchbox.client.JestClient
import io.searchbox.core.Bulk
import io.searchbox.core.Index
import io.searchbox.core.Search
import io.searchbox.indices.CreateIndex
import io.searchbox.indices.DeleteIndex
import io.searchbox.indices.IndicesExists
import org.elasticsearch.common.geo.GeoPoint
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.IOException
import java.text.MessageFormat


@Component
class SearchService<T> {


    val logger = LoggerFactory.getLogger(SearchService::class.java)

    private val INDEX = "property"

    private val INDEX_TYPE = "property"

    private val MAX_SIZE_PAGE = 20

    @Autowired
    private lateinit var jestClient: JestClient

    private fun deleteIndexIfExists(index: String) {
        logger.info("[deleteIndexIfExists]")
        val deleteIndex = DeleteIndex.Builder(index).build()
        try {
            jestClient.execute(deleteIndex)
        } catch (ex: IOException) {
            logger.error("ERROR DELETING INDEX", ex)
        }

    }

     fun createIndexIfNotExists(index: String) {
        logger.info("[createIndexIfNotExists]")
        val indicesExists = IndicesExists.Builder(index).build()
        try {
            val result = jestClient.execute(indicesExists)
            if (!result.isSucceeded) {
                // Create articles index
                val createIndex = CreateIndex.Builder(index).build()
                val resultCreate = jestClient.execute(createIndex)
                if (resultCreate.isSucceeded) {
                    logger.info(MessageFormat.format("{0} INDEX CREATED", index))
                } else {
                    throw IllegalStateException("ERROR CREATING INDEX")
                }

            } else {
                logger.info(MessageFormat.format("{0} ALREADY EXISTS", index))
            }
        } catch (ex: IOException) {
            logger.error("ERROR CREATING INDEX", ex)
        }

    }

    fun create(element: T) {

        logger.info("[createElement]")

        try {

            val strategy = object : ExclusionStrategy {
                override fun shouldSkipField(field: FieldAttributes): Boolean {
                    if (field.declaringClass == Location::class.java && field.name == "id") {
                        return true
                    }
                    return false
                }

                override fun shouldSkipClass(clazz: Class<*>): Boolean {
                    return false
                }
            }

            val json = GsonBuilder().addSerializationExclusionStrategy(strategy).create().toJson(element)
            val gson = JsonParser.parseString(json)
            val bulk = Bulk.Builder()
                    .addAction(Index.Builder(gson)
                            .index(INDEX)
                            .type(INDEX_TYPE).build())
                    .build()

            val result = jestClient.execute(bulk)
            if (result.isSucceeded) {
                logger.info("CREATED")
            } else {
                throw IllegalStateException("ERROR CREATING INDEX")
            }
            logger.info(result.jsonString)

        } catch (e: IOException) {
            logger.error("ERROR IO INDEXING", e)
        } catch (e: Exception) {
            logger.error("ERROR INDEXING", e)
        }

    }

    fun searchByQueryString(param: String): List<PropertyDto> {
        logger.info("[searchArticles]")
        logger.info(param)
        try {
            val searchSourceBuilder = SearchSourceBuilder()
            searchSourceBuilder.query(QueryBuilders.queryStringQuery(param))
            searchSourceBuilder.size(MAX_SIZE_PAGE)

            logger.info(searchSourceBuilder.toString())
            val search = Search.Builder(searchSourceBuilder.toString()).addIndex(INDEX).addType(INDEX_TYPE)
                    .build()

            val result = jestClient.execute(search)
            if (result.isSucceeded) {
                logger.info(result.sourceAsString)

            } else {
                throw SearchServiceException(result.errorMessage)
            }

            return result.getSourceAsObjectList(PropertyDto::class.java, false)

        } catch (e: IOException) {
            logger.error("SEARCH IO ERROR", e)
        } catch (e: Exception) {
            logger.error("SEARCH ERROR", e)
        }

        return mutableListOf()
    }


}
