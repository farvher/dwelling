package com.dwelling.app.services.multimedia

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile
import java.util.stream.Stream

interface MultimediaStorageService {

    fun store(userId : Long, folder : String, filename: String, file: MultipartFile)

    fun count(userId: Long, folder : String) : Int

    fun listAll(userId : Long, folder : String) : Stream<String>

    fun getFile(userId : Long , folder : String,filename : String) : String

    fun getFileAsResource(userId : Long , folder : String, filename : String) : Resource

    fun delete(userId : Long,  folder : String,filename : String)

    fun deleteAll(userId : Long)


}
