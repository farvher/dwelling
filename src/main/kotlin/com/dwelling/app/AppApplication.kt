package com.dwelling.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * MINIMO PRODUCTO VIABLE
 *
 * SERVICIO REST DE LISTADO DE INMUEBLES - BUSCADOR Y FILTROS
 * SERVICIO REST DEL DETALLE DEL INMUEBLE
 * SERVICIO REST PARA PUBLICAR INMUEBLE
 * SERVICIO DE AUTENTICACION
 *
 * SERVICIO REST DE PREFERENCIAS DE USUARIO
 *
 *
 * */
@SpringBootApplication
open class AppApplication

    fun main(args: Array<String>) {
        runApplication<AppApplication>(*args)
    }



