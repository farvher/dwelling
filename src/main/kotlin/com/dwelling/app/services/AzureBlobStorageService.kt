package com.dwelling.app.services

import com.azure.storage.blob.BlobClient
import com.azure.storage.blob.BlobContainerClient
import com.azure.storage.blob.BlobServiceClient
import com.azure.storage.blob.BlobServiceClientBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.stream.Stream
import javax.activation.MimetypesFileTypeMap
import javax.annotation.PostConstruct

/**
 * Maneja la carga y descarga de archivos al contenedor de Azure
 *
 * @author fsanmiguel
 *
 * */
@Service
class AzureBlobStorageService : StorageService {

    val logger: Logger = LoggerFactory.getLogger(AzureBlobStorageService::class.java)


    @Value("\${azure.connectionString}")
    private lateinit var azureConnectionString: String

    @Value("\${azure.key}")
    private lateinit var azureKey: String

    @Value("\${azure.container}")
    private lateinit var azureContainer: String

    @Value("\${filesystem.image.tmp}")
    private lateinit var localTmpFolder: String

    private lateinit var blobContainerClient: BlobContainerClient

    private lateinit var blobServiceClient: BlobServiceClient

    private lateinit var blobClient: BlobClient

    @PostConstruct
    override fun init() {
        val azureSecret = System.getenv(azureConnectionString)
        blobServiceClient = BlobServiceClientBuilder().connectionString(azureSecret)
                .buildClient()
        blobContainerClient = blobServiceClient.getBlobContainerClient(azureContainer)

        if (!Files.exists(Paths.get(localTmpFolder))) {
            Files.createDirectory(Paths.get(localTmpFolder))
        }

    }

    override fun storage(file: MultipartFile, path: String) {
        val absolutePath = Paths.get(localTmpFolder).resolve(path)
        logger.info("[storage] $absolutePath")
        if (!Files.exists(absolutePath.parent)) {
            Files.createDirectories(absolutePath.parent)
        }
        Files.copy(file.inputStream, absolutePath, StandardCopyOption.REPLACE_EXISTING)
        var blobClient = blobContainerClient.getBlobClient(path)
        blobClient.uploadFromFile(absolutePath.toString(), true)
        Files.deleteIfExists(absolutePath)
    }

    override fun count(filename: String): Int {
        val folder = "$azureContainer/$filename"
        if(!blobContainerClient.getBlobClient(folder).exists()) return 0
        return blobContainerClient.listBlobs().count()
    }

    override fun delete(filename: String) {
        blobContainerClient.getBlobClient(filename).delete()
    }

    override fun loadAll(): Stream<Path> {
        return blobContainerClient.listBlobs().mapPage { b -> Path.of(b.name) }.stream()
    }

    override fun load(filename: String): Path {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadAsResource(filename: String): Resource {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}