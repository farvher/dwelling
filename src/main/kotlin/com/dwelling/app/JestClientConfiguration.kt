package com.dwelling.app

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


import io.searchbox.client.JestClient
import io.searchbox.client.JestClientFactory
import io.searchbox.client.config.HttpClientConfig
import org.springframework.beans.factory.annotation.Value

@Configuration
class JestClientConfiguration {

    private val logger = LoggerFactory.getLogger(JestClientConfiguration::class.java)

    @Value("\${elastic.connection}")
    private lateinit var elasticSearchConnection: String

    @Bean
    @Throws(Exception::class)
    fun jestClient(): JestClient {
        val factory = JestClientFactory()
        factory.setHttpClientConfig(HttpClientConfig
                .Builder(elasticSearchConnection)
                .multiThreaded(true)
                .build())
        return factory.getObject()
    }

}
