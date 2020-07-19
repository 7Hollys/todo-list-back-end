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
    @GetMapping("test")
    fun selectLastYearAmount(): String {
        return "Test !!!";
    }

    @GetMapping("cicd_test")
    fun cicdTest(): String {
        return "Success CICD TODO List 2020-07-07 10시"
    }
}