package com.hollys.todoList.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "todo_list")
data class TodoList(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @Column(name = "user_id", nullable = false)
        val userId: Long,

        @Column(name = "contents")
        val contents: String,

        @Column(name = "sequence")
        val sequence: Long,

        @Column(name = "is_checked")
        val isChecked: Boolean,

        @Column(name = "created_at")
        val createdAt: Date,

        @Column(name = "updated_at")
        val updatedAt: Date,

        @Column(name = "deleted_at")
        val deletedAt: Date
        ) {
}