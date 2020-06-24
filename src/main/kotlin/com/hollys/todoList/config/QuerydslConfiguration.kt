package com.hollys.todoList.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


// 프로젝트에서는 어느 곳에서나 JPAQueryFactory를 주입 받아 Querydsl을 사용가능
@Configuration
class QuerydslConfiguration(
        @PersistenceContext
        private val entityManager: EntityManager
) {

    @Bean
    fun jpaQueryFactory(): JPAQueryFactory {
        return JPAQueryFactory(entityManager)
    }

}