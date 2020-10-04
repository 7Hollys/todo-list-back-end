package com.hollys.todoList.repo

import com.hollys.todoList.entity.TodoList
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

import org.springframework.stereotype.Repository
import java.time.Instant
import javax.transaction.Transactional

@Repository
interface TodoListRepository : JpaRepository<TodoList, Long> {

    @Transactional
    @Modifying
    @Query(value = "update TodoList a set a.deletedAt = :deletedAt where a.id = :id", nativeQuery = false)
    fun updateToDelete(@Param("deletedAt") deletedAt: Instant, @Param("id") id: Long)

//    @Transactional
//    @Modifying
//    @Query(value = "delete from TodoList a where a.id = :id", nativeQuery = false)
//    override fun deleteById(@Param("id") id: Long)

}