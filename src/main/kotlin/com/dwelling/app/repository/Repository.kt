package com.dwelling.app.repository

import com.dwelling.app.domain.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface VisitorRepository : JpaRepository<Visitor,Long> {

    fun findVisitorByUsername(username:String?) : Optional<Visitor>
}

@Repository
interface  VisitorPreferencesRepository : JpaRepository<VisitorPreferences,Long>


@Repository
interface PropertyRepository : JpaRepository<Property,Long>

@Repository
interface RealStateRepository  : JpaRepository<RealState,Long>

@Repository
interface BuilderRepository : JpaRepository<Builder,Long>

@Repository
interface AdditionalRepository : JpaRepository<Additional,Long>

@Repository
interface CityRepository : JpaRepository<City,Long>

@Repository
interface FavoritesRepository : JpaRepository<VisitorFavorites,Long>{

    fun findByVisitor(idVisitor :Long) : List<VisitorFavorites>

}


