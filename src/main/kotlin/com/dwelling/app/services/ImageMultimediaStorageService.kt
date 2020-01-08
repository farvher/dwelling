package com.dwelling.app.services

import com.dwelling.app.exceptions.IsNotImageException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.stream.Stream
import javax.imageio.ImageIO

@Service
class ImageMultimediaStorageService : MultimediaStorageService {

    val logger: Logger = LoggerFactory.getLogger(ImageMultimediaStorageService::class.java)

    @Value("\${image.ext}")
    private lateinit var imgExt : String


    @Autowired
    @Qualifier("azureBlobStorageService")
    private lateinit var azureBlobStorageService: StorageService

    override fun store(userId: Long, folder: String, filename: String, file: MultipartFile) {

        if(!isImage(file)) {
            throw IsNotImageException("${file.originalFilename} is not a image!")
        }
        var filename : String = "$userId/$folder/$filename$imgExt"
        azureBlobStorageService.storage(file,filename)
    }

    override fun count(userId: Long, folder: String): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun listAll(userId: Long, folder: String): Stream<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFile(userId: Long, folder: String, filename: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFileAsResource(userId: Long, folder: String, filename: String): Resource {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(userId: Long, folder: String, filename: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAll(userId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun isImage(file: MultipartFile): Boolean {
        return ImageIO.read(file.inputStream) != null
    }
}