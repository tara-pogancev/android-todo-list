package com.tarapogancev.todolist.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tarapogancev.todolist.model.TodoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoTaskDao {

    @Query("select * from todoTasks")
    fun getTasks(): Flow<List<TodoTask>>

    @Query("select * from todoTasks")
    fun getTasksList(): List<TodoTask>

    @Query("select * from todoTasks where id = :id")
    fun getTask(id: Long): Flow<TodoTask>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: TodoTask)

    @Update
    suspend fun update(task: TodoTask)

    @Delete
    suspend fun delete(task: TodoTask)

}