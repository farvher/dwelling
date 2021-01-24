package com.dwelling.app


import io.searchbox.client.JestClient
import io.searchbox.client.JestClientFactory
import io.searchbox.client.config.HttpClientConfig
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JestClientConfiguration {

    private val logger = LoggerFactory.getLogger(JestClientConfiguration::class.java)

    @Value("\${elastic.connection}")
    private lateinit var elasticSearchConnection: String

    @Value("\${spring.data.elasticsearch.client.reactive.username}")
    private lateinit var elasticSearchUser: String

    @Value("\${spring.data.elasticsearch.client.reactive.password}")
    private lateinit var elasticSearchPassword: String


    @Bean
    @Throws(Exception::class)
    fun jestClient(): JestClient {
        val factory = JestClientFactory()
        factory.setHttpClientConfig(
            HttpClientConfig
                .Builder(elasticSearchConnection)
                .multiThreaded(true)
                .build()
        )
        return factory.getObject()
    }

}
