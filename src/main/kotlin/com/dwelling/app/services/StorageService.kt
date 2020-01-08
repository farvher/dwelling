package com.dwelling.app.services

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path
import java.util.stream.Stream

interface StorageService {

    fun init()

    fun storage(file: MultipartFile, path : String)

    fun count(filename: String) : Int

    fun delete(filename: String)

    fun loadAll() : Stream<Path>

    fun load(filename : String) : Path

    fun loadAsResource(filename:String) : Resource

    fun deleteAll()

}