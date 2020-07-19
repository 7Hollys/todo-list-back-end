package com.hollys.todoList.dto

import javax.validation.constraints.NotBlank

data class TodoListUpdateRequest(
        @NotBlank val id: Long,
        @NotBlank val userId: Long,
        @NotBlank val contents: String,
        @NotBlank val sequence: Long,
        @NotBlank val isChecked: Boolean
)