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
import java.nio.file.Path
import java.util.stream.Stream
import javax.annotation.PostConstruct


@Service
class AzureBlobStorageService : StorageService {

    val logger: Logger = LoggerFactory.getLogger(AzureBlobStorageService::class.java)


    @Value("\${azure.connectionString}")
    private lateinit var azureConnectionString: String

    @Value("\${azure.key}")
    private lateinit var azureKey: String

    @Value("\${azure.container}")
    private lateinit var azureContainer: String

    private lateinit var blobContainerClient: BlobContainerClient

    private lateinit var blobServiceClient: BlobServiceClient

    private lateinit var blobClient: BlobClient

    @PostConstruct
    override fun init()  {

        val azureSecret = System.getenv(azureConnectionString)

        blobServiceClient = BlobServiceClientBuilder().connectionString(azureSecret)
                .buildClient()

        blobContainerClient = blobServiceClient.getBlobContainerClient(azureContainer)

        logger.info("!!!!!!!!!!!!!!!!")
        for (blobItem in blobContainerClient.listBlobs()) {
            logger.info("\t" + blobItem.name)
        }



    }

    override fun storage(file: MultipartFile) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadAll(): Stream<Path> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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