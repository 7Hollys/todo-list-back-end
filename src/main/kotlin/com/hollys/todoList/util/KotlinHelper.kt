package com.hollys.todoList.util

import java.util.*


fun <T : Any> Optional<T>.toNullable(): T? = this.orElse(null)