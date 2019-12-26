package com.dwelling.app.domain


enum class PropertyTypeEnum(single: String, plural: String) {
    APARTAMENTO("APARTAMENTO", "APARTAMENTOS"),
    CASA("CASA", "CASAS"),
    LOTE("LOTE", "LOTES"),
    OFICINA("OFICINA", "OFICINAS"),
    BODEGA("BODEGA", "BODEGAS"),
    LOCAL("LOCAL", "LOCALES"),
    APARTAESTUDIO("APARTAESTUDIO", "APARTAESTUDIOS"),
    CONSULTORIO("CONSULTORIO","CONSULTORIOS")
}

enum class BusinessTypeEnum(single: String) {
    VENTA("VENTA"),
    ARRIENDO("ARRIENDO"),
    VENTA_Y_ARRIENDO("VENTA Y ARRIENDO"),
    VACACIONAL("VACACIONAL")
}

enum class GenderEnum(name: String) {
    MALE("MASCULINO"),
    FEMALE("FEMENINO")
}

