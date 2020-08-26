package com.dwelling.app.elasticsearch

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import io.searchbox.client.JestClient
import io.searchbox.core.Bulk
import io.searchbox.core.Index
import io.searchbox.indices.CreateIndex
import io.searchbox.indices.DeleteIndex
import io.searchbox.indices.IndicesExists
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class ElasticIndexService<T> {

    val logger: Logger = LoggerFactory.getLogger(ElasticIndexService::class.java)

    @Autowired
    private lateinit var jestClient: JestClient

    /**
     * Create elasticsearch element
     *@param element
     * */
    fun createElement(element: T,index: String, type:String) {
        logger.info("[createElement]")
        try {
            val json = GsonBuilder().create().toJson(element)
            val parseString = JsonParser.parseString(json)
            val bulk = Bulk.Builder()
                    .addAction(Index.Builder(parseString)
                            .index(index)
                            .type(type).build())
                    .build()
            val result = jestClient.execute(bulk)
            if (!result.isSucceeded) {
                throw IllegalStateException("Cannot create element $parseString")
            }
            logger.info(result.jsonString)

        } catch (e: Exception) {
            logger.error("Error creating element", e)
        }
    }


    /**
     * Delete index if exists
     * @param index
     * */
    fun deleteIndexIfExists(index: String) {
        logger.info("[deleteIndexIfExists]")
        val deleteIndex = DeleteIndex.Builder(index).build()
        try {
            jestClient.execute(deleteIndex)
        } catch (ex: IOException) {
            logger.error("Error deleting $index Index ", ex)
        }
    }

    /**
     * create elasticsearch index
     * @param index
     * */
    fun createIndexIfNotExists(index: String) {
        logger.info("[createIndexIfNotExists]")
        val indicesExists = IndicesExists.Builder(index).build()
        try {
            val result = jestClient.execute(indicesExists)
            if (!result.isSucceeded) {
                val createIndex = CreateIndex.Builder(index).build()
                val resultCreate = jestClient.execute(createIndex)
                if (resultCreate.isSucceeded) {
                    logger.info("$index Index crated")
                } else {
                    throw IllegalStateException("Error creating index")
                }
            } else {
                logger.info("$index Index already exists")
            }
        } catch (ex: Exception) {
            logger.error("Exception creating $index Index ", ex)
        }

    }



}
