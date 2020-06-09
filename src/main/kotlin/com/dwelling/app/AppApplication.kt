package com.dwelling.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

//import org.springframework.cloud.netflix.eureka.EnableEurekaClient

/**
 * Dwelling - Busqueda y publicacion de inmuebles
 * @author Farith Sanmiguel
 * @since 1.0.0
 * */
@SpringBootApplication
class AppApplication

fun main(args: Array<String>) {
    runApplication<AppApplication>(*args)

}

