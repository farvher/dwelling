package com.dwelling.app.repository

import com.dwelling.app.domain.*
import com.dwelling.app.dto.PropertyDto
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*


@Repository
interface VisitorRepository : ReactiveCrudRepository<Visitor, Long> {

    fun findVisitorByUsername(username: String?): Optional<Visitor>
}

@Repository
interface RealStateRepository : ReactiveCrudRepository<RealState, Long>

@Repository
interface BuilderRepository : ReactiveCrudRepository<Builder, Long>


@Repository
interface UserRepository : ReactiveCrudRepository<User, Long> {

    fun findByUsername(username: String): Mono<User>

    @Query(
        "SELECT a.* FROM Authority a " +
                "INNER JOIN user_authority ua on a.id = ua.authority_id " +
                "INNER JOIN user_app u on ua.user_id = u.id WHERE u.username = :username"
    )
    fun findAuthorities(username: String): Flux<Authority>
}


@Repository
interface ElasticPropertyRepository : ReactiveElasticsearchRepository<PropertyDto, Long>

