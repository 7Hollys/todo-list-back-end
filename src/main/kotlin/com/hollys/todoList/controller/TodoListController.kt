package com.hollys.todoList.controller

import com.querydsl.jpa.impl.JPAQueryFactory
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping("api/todo_list")
class TodoListController(private var queryFactory: JPAQueryFactory) {


}