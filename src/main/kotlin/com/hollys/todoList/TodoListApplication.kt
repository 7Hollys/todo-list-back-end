package com.hollys.todoList

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties
@SpringBootApplication
class TodoListApplication

fun main(args: Array<String>) {
	runApplication<TodoListApplication>(*args)
}
