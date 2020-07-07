package com.hollys.todoList.controller

import com.hollys.todoList.auth.util.UserPrincipal
import com.hollys.todoList.dto.TodoListSaveRequest
import com.hollys.todoList.dto.TodoListUpdateRequest
import com.hollys.todoList.entity.QTodoList.todoList
import com.hollys.todoList.entity.TodoList
import com.hollys.todoList.repo.TodoListRepository
import com.hollys.todoList.util.OAuth2AuthenticationProcessingException
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("api/todo_list")
class TodoListController(
        @Autowired private val todoListRepository: TodoListRepository,
        @Autowired private var queryFactory: JPAQueryFactory
) {

    @PutMapping("update")
    fun update(
            @Valid @RequestBody todoListRequest: TodoListUpdateRequest
    ): ResponseEntity<TodoList> {
        val user: UserPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        val todoList: Optional<TodoList> = todoListRepository.findById(todoListRequest.id)
        if (user.id != todoListRequest.userId || todoListRequest.userId != todoList.get().userId)
            throw OAuth2AuthenticationProcessingException("It's not your writing.")
        val modifiedTodoList = TodoList(
                id = todoList.get().id,
                userId = todoList.get().userId,
                contents = todoListRequest.contents,
                sequence = todoListRequest.sequence,
                isChecked = todoListRequest.isChecked,
                createdAt = todoList.get().createdAt,
                updatedAt = Date(),
                deletedAt = null
        )
        return ResponseEntity.ok(todoListRepository.save(modifiedTodoList))
    }

    @PostMapping("save")
    fun save(
            @Valid @RequestBody todoListRequest: TodoListSaveRequest
    ): ResponseEntity<TodoList> {
        val user: UserPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        val registerNewTodoList = TodoList(
                id = null,
                userId = user.id,
                contents = todoListRequest.contents,
                sequence = todoListRequest.sequence,
                isChecked = todoListRequest.isChecked,
                createdAt = Date(),
                updatedAt = null,
                deletedAt = null
        )
        return ResponseEntity.ok(todoListRepository.save(registerNewTodoList))
    }

    @GetMapping("select")
    fun selectTodoList(
            @RequestParam("sequence", defaultValue = "0") sequence: Long,
            @RequestParam("limit", defaultValue = "10") limit: Long
    ): List<TodoList> {
        val user: UserPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        return queryFactory
                .selectFrom(todoList)
                .where(todoList.deletedAt.isNull, todoList.userId.eq(user.id), todoList.sequence.gt(sequence))
                .orderBy(todoList.sequence.asc())
                .limit(limit)
                .fetch()
    }


}
