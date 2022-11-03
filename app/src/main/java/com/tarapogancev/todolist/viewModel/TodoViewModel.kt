package com.tarapogancev.todolist.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tarapogancev.todolist.model.TodoTask

class TodoViewModel : ViewModel() {

    private val _tasks = MutableLiveData<MutableList<TodoTask>>(mutableListOf())
    val tasks: LiveData<MutableList<TodoTask>> = _tasks

    fun addNewTask(newTask: TodoTask) {
        _tasks.value?.add(newTask)
    }


}