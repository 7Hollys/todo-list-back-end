package com.hollys.todoList.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "todo_list")
data class TodoList(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,

        @Column(name = "user_id")
        val userId: Long,

        @Column(name = "contents")
        val contents: String,

        @Column(name = "sequence")
        val sequence: Long,

        @Column(name = "is_checked")
        val isChecked: Boolean,

        @Column(name = "created_at", insertable = false, updatable = false, nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
        val createdAt: Date?,

        @Column(name = "updated_at", insertable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
        val updatedAt: Date?,

        @Column(name = "deleted_at", insertable = false, columnDefinition = "datetime")
        val deletedAt: Date?
)