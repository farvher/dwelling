package com.dwelling.app

import com.azure.storage.blob.BlobServiceClientBuilder
import com.dwelling.app.elasticsearch.IDwellingsSeach
import io.searchbox.client.JestClientFactory
import io.searchbox.client.config.HttpClientConfig
import io.searchbox.core.Search
import org.elasticsearch.common.unit.TimeValue
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import java.util.concurrent.TimeUnit
import javax.sql.DataSource


@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConnectionTests {

    val logger: Logger = LoggerFactory.getLogger(ConnectionTests::class.java)

    @Value("\${database.connection}")
    private lateinit var databaseConnection: String

    @Value("\${azure.connection}")
    private lateinit var azureConnection: String

    @Value("\${azure.key}")
    private lateinit var azureKey: String

    @Value("\${elastic.connection}")
    private lateinit var elasticSearchConnection: String

    @Autowired
    private lateinit var datasource: DataSource


    @Test
    @Ignore
    fun shouldConnectToAzure(): Unit {
        assert(!azureConnection.isNullOrBlank())
        val blobServiceClient = BlobServiceClientBuilder()
                .connectionString(azureConnection)
                .buildClient()
        assert(blobServiceClient.accountName == "dwellingpics")
    }

    @Test
    @Ignore
    fun shouldConnectToDatabase(): Unit {
        assert(!databaseConnection.isNullOrBlank())
        val connection = datasource.connection
        connection.use { logger.info("Database connection : $it.catalog") }
    }

    @Test
    @Ignore
    fun shouldConnectToElasticSearch(): Unit {
        assert(!elasticSearchConnection.isNullOrBlank())
        val factory = JestClientFactory()
        factory.setHttpClientConfig(HttpClientConfig
                .Builder(elasticSearchConnection)
                .multiThreaded(true)
                .build())
        val sourceBuilder = SearchSourceBuilder()
        sourceBuilder.from(0)
        sourceBuilder.size(5)
        sourceBuilder.timeout(TimeValue(60, TimeUnit.SECONDS))
        val searchBuilder = sourceBuilder.query(QueryBuilders.matchAllQuery())
        val search = Search.Builder(sourceBuilder.toString())
                .addType(IDwellingsSeach.TYPE_PROPERTY)
                .addIndex(IDwellingsSeach.INDEX_PROPERTY)
                .build()
        val result = factory.getObject().execute(search)
        assert(result.isSucceeded)
    }


}