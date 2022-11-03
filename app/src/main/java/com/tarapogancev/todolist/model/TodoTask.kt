package com.tarapogancev.todolist.model

import java.io.Serializable

data class TodoTask (
    var id: Long,
    var taskTitle: String,
    var isFinished: Boolean
) : Serializable