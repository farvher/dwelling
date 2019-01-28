package com.dwelling.app.repository

import com.dwelling.app.domain.Property
import com.dwelling.app.domain.Visitor
import org.springframework.data.jpa.repository.JpaRepository


//interface PropertyRepository : JpaRepository<Property,Long>

interface VisitorRepository : JpaRepository<Visitor,Long>
