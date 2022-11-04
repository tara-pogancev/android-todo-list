package com.tarapogancev.todolist

import android.app.Application
import com.tarapogancev.todolist.model.TodoDatabase

class BaseApplication: Application() {
    val database: TodoDatabase by lazy {
        TodoDatabase.getDatabase(this)
    }
}