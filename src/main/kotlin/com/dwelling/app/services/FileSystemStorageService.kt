package com.dwelling.app.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.stream.Stream
import javax.annotation.PostConstruct


@Service
class FileSystemStorageService : StorageService {

    val logger: Logger = LoggerFactory.getLogger(FileSystemStorageService::class.java)

    @Value("\${filesystem.root.path}")
    private lateinit var rootPath: String

    private lateinit var rootLocation: Path

    @PostConstruct
    override fun init() {
        rootLocation = Paths.get(rootPath)
        //Files.createDirectory(rootLocation)

    }

    override fun storage(file: MultipartFile) {
        val filename: String = StringUtils.cleanPath(file.originalFilename!!)
        logger.info("[saving file  $filename]")
        try {
            if (file.isEmpty) {
                throw FileNotFoundException("Failed to store empty file $filename")
            }
            if (filename.contains("..")) { // This is a security check
                throw IllegalStateException("Cannot store file with relative path outside current directory $filename")
            }
            file.inputStream.use { inputStream ->
                Files.copy(inputStream, rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING)
            }
        } catch (e: IOException) {
            throw IOException("Failed to store file $filename", e)
        }
    }

    override fun loadAll(): Stream<Path> {
        return try {
            Files.walk(rootLocation, 1)
                    .filter { path -> path != rootLocation }
                    .map { other -> rootLocation.relativize(other) }
        } catch (e: IOException) {
            throw IOException("Failed to read stored files", e)
        }
    }

    override fun load(filename: String): Path {
        return rootLocation.resolve(filename)
    }

    override fun loadAsResource(filename: String): Resource {
        return try {
            val file = load(filename)
            val resource: Resource = UrlResource(file.toUri())
            if (resource.exists() || resource.isReadable) {
                resource
            } else {
                throw IOException("Could not read file: $filename")
            }
        } catch (e: Exception) {
            throw Exception("Could not read file: $filename", e)
        }
    }

    override fun deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile())
    }


}