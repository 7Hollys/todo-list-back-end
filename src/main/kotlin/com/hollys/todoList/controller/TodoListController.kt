package com.hollys.todoList.controller

import com.hollys.todoList.auth.util.UserPrincipal
import com.hollys.todoList.dto.TodoListRequest
import com.hollys.todoList.entity.QTodoList.todoList
import com.hollys.todoList.entity.TodoList
import com.hollys.todoList.repo.TodoListRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/todo_list")
class TodoListController(
        @Autowired private val todoListRepository: TodoListRepository,
        @Autowired private var queryFactory: JPAQueryFactory
) {

    @PostMapping("save")
    @ResponseStatus(HttpStatus.OK)
    fun save(
            @Valid @RequestBody todoListRequest: TodoListRequest
    ): ResponseEntity<TodoList> {
        val user: UserPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        val todoList = TodoList(
                id = null,
                userId = user.id,
                contents = todoListRequest.contents,
                sequence = todoListRequest.sequence,
                isChecked = todoListRequest.isChecked,
                createdAt = null,
                updatedAt = null,
                deletedAt = null
        )
        return ResponseEntity.ok(todoListRepository.save(todoList))
    }

    @GetMapping("select")
    @ResponseStatus(HttpStatus.OK)
    fun selectTodoList(
            @RequestParam("sequence", defaultValue = "0") sequence: Long,
            @RequestParam("limit", defaultValue = "10") limit: Long
    ): List<TodoList> {
        val user: UserPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        return queryFactory
                .selectFrom(todoList)
                .where(todoList.userId.eq(user.id), todoList.sequence.gt(sequence))
                .orderBy(todoList.sequence.asc())
                .limit(limit)
                .fetch()
    }

        
        

}
