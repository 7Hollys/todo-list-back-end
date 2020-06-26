package com.hollys.todoList.controller

import com.hollys.todoList.entity.QUser.user
import com.hollys.todoList.entity.User
import com.querydsl.jpa.impl.JPAQueryFactory
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Slf4j
@RestController
@RequestMapping("api")
class TestController(
        private var queryFactory: JPAQueryFactory
) {

//    fun TestController(queryFactory: JPAQueryFactory) {
//        this.queryFactory = queryFactory
//    }

    @GetMapping("test")
    fun selectLastYearAmount(): String {
        return "Test !!!";
    }

    @GetMapping("user_info")
    fun selectUserInfo(): List<User> {
        val result: List<User> = queryFactory.selectFrom(user).fetch()
        return result
    }
}