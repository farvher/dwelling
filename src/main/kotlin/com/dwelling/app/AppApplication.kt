package com.dwelling.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient

/**
 * MINIMO PRODUCTO VIABLE
 *
 * SERVICIO REST DE LISTADO DE INMUEBLES - BUSCADOR Y FILTROS oOK
 *
 * SERVICIO REST DEL DETALLE DEL INMUEBLE - OK
 * SERVICIO REST PARA PUBLICAR INMUEBLE (foto , datos , ubicacion y planes ,geolocation, login)
 * SERVICIO DE AUTENTICACION -- OK AUTENTICACION CON TOKEN
 *
 * SERVICIO REST DE PREFERENCIAS DE USUARIO
 *
 *
 * */
@SpringBootApplication
//@EnableEurekaClient
class AppApplication

    fun main(args: Array<String>) {
        runApplication<AppApplication>(*args)
    }



