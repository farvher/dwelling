package com.dwelling.app.domain

import org.springframework.data.annotation.Id
import java.time.LocalDate


data class CV(@Id val id: Long,
              val userId: Long,
              val myCVs: List<CVFull> = emptyList(),
              val successfullyCreated : Boolean = false
)

data class CVFull(@Id val id: Long,
                  val basicInformation: CVBasicInfo? = null,
                  var education: List<CVEducation>? = emptyList(),
                  var work: List<CVWork>? = emptyList(),
                  var summary: CVSummary? = null

)


data class CVWork(val jobTitle: String,
                  val employer: String,
                  val city: String,
                  val state: String,
                  val startDate: LocalDate,
                  val endDate: LocalDate?,
                  val currentlyWork: Boolean,
                  val workDetail: List<String>)

data class CVEducation(val schoolName: String,
                       val degree: String,
                       val startDate: LocalDate,
                       val endDate: LocalDate
)

data class CVSummary(val summary: String,
                     val skills: List<String>,
                     val additionalInfo: String?,
                     val certifications: List<String>?,
                     val interests: String?
)

data class CVBasicInfo(val firstName: String,
                       val lastName: String,
                       val profession: String,
                       val city: String,
                       val state: String,
                       val email: String)

