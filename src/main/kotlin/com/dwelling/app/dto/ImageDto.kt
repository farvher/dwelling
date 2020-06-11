package com.dwelling.app.dto

import org.springframework.web.multipart.MultipartFile

data class ImageDto(val id: Long,
                    val userId: Long,
                    val folder: String,
                    val imageMultipartFile: MultipartFile,
                    var url :String ? = ""
) {


}