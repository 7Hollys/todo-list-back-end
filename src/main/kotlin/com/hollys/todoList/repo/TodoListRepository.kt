package com.hollys.todoList.repo

import com.hollys.todoList.entity.TodoList
import org.springframework.data.jpa.repository.JpaRepository

import org.springframework.stereotype.Repository

@Repository
interface TodoListRepository : JpaRepository<TodoList, Long> {

}