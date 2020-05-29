package com.dwelling.app.cvcontroller

import com.dwelling.app.domain.CV
import com.dwelling.app.domain.CVBasicInfo
import com.dwelling.app.domain.CVFull
import com.dwelling.app.elasticsearch.CVIndexServiceImpl
import com.dwelling.app.elasticsearch.ICVIndexService
import com.dwelling.app.services.VisitorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * Controllador para manejar CV
 * @author fsanmiguel
 * */
@RestController
class CVController {


    @Autowired
    private lateinit var cvIndexService: ICVIndexService

    @Autowired
    private lateinit var visitorService: VisitorService


    /**
     *
     * */
    @PostMapping(value = ["/createcv"])
    fun createBasicInfo(@RequestBody basicInfo: CVBasicInfo, request: HttpServletRequest): CV {
        val visitor = visitorService.getVisitor(request)
        val sessionId = request.session.id
        val id = 1L
        val cvFull = CVFull(1, basicInfo)
        val cv = CV(id, visitor.id!!, listOf(cvFull))
        cvIndexService.saveCV(cv)
        return cv
    }


    @GetMapping(value = ["/querycv"])
    fun queryCV(@RequestParam id : Long,request: HttpServletRequest) : CV{
        val visitor = visitorService.getVisitor(request)
        return cvIndexService.getCVById(id)
    }

}
