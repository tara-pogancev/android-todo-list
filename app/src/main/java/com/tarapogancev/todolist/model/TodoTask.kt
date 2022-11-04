package com.tarapogancev.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "todoTasks")
data class TodoTask (
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var taskTitle: String,
    var description: String,
    var isFinished: Boolean
) : Serializable
