package com.dwelling.app.domain


enum class PropertyTypes(single: String, plural: String) {
    APARTAMENTO("APARTAMENTO", "APARTAMENTOS"),
    CASA("CASA", "CASAS"),
    LOTE("LOTE", "LOTES"),
    OFICINA("OFICINA", "OFICINAS"),
    BODEGA("BODEGA", "BODEGAS"),
    LOCAL("LOCAL", "LOCALES"),
    APARTAESTUDIO("APARTAESTUDIO", "APARTAESTUDIOS")
}

enum class BusinessType(single: String) {
    VENTA("VENTA"),
    ARRIENDO("ARRIENDO"),
    VENTA_Y_ARRIENDO("VENTA Y ARRIENDO"),
    VACACIONAL("VACACIONAL")
}

enum class Gender(name: String) {
    MALE("MASCULINO"),
    FEMALE("FEMENINO")
}

