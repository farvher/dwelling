package com.dwelling.app

import com.dwelling.app.controllers.ResultsController
import com.dwelling.app.domain.Property
import com.dwelling.app.elasticsearch.ElasticIndexService
import com.dwelling.app.elasticsearch.IDwellingsSeach
import com.dwelling.app.repository.PropertyRepository
import com.dwelling.app.security.controller.UserRestController
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner


@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ResultsTests {

    val logger: Logger = LoggerFactory.getLogger(ResultsTests::class.java)
    @Autowired
    private lateinit var searchService: ElasticIndexService<Property>
    @Autowired
    private lateinit var dwellingsSearch: IDwellingsSeach
    @Autowired
    private lateinit var userRestController: UserRestController
    @Autowired
    private lateinit var propertyRepository: PropertyRepository

    @Test
    fun findByLocations(): Unit {
        
    }

    


}