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
interface  VisitorPreferencesRepository : JpaRepository<VisitorPreferences,Long>{
    fun findByVisitor(idVisitor : Long) : Optional<VisitorPreferences>
}


@Repository
interface PropertyRepository : JpaRepository<Property,Long>

@Repository
interface RealStateRepository  : JpaRepository<RealState,Long>

@Repository
interface BuilderRepository : JpaRepository<Builder,Long>

@Repository
interface AdditionalRepository : JpaRepository<Additional,Long>


@Repository
interface FavoritesRepository : JpaRepository<VisitorFavorite,Long>{
    fun findByVisitor(visitor: Visitor) : Optional<List<VisitorFavorite>>
}

@Repository
interface CityRepository : JpaRepository<City,Long>{
    fun findByName(name: String) : Optional<City>
}

@Repository
interface ZoneRepository : JpaRepository<Zone,Long>{
    fun findByName(name : String) : Optional<Zone>
}

@Repository
interface NeighborhoodRepository : JpaRepository<Neighborhood,Long>{
    fun findByName(name:String) : Optional<Neighborhood>
}

@Repository
interface CountryRepository : JpaRepository<Country,Long>{
    fun findByName(name:String) : Optional<Country>
}




