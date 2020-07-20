package com.hollys.todoList.dto

import javax.validation.constraints.NotBlank

data class TodoListSaveRequest(
        @NotBlank val contents: String,
        @NotBlank val isChecked: Boolean
)