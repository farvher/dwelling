package com.dwelling.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory

import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.stereotype.Component


//import org.springframework.cloud.netflix.eureka.EnableEurekaClient

/**
 * Dwelling - Busqueda y publicacion de inmuebles
 * @author Farith Sanmiguel
 * @since 1.0.0
 * */
@SpringBootApplication
@EnableR2dbcRepositories
@EnableWebFlux
class AppApplication

fun main(args: Array<String>) {
    runApplication<AppApplication>(*args)

}

