package com.tarapogancev.todolist.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tarapogancev.todolist.model.TodoTask

class TodoViewModel : ViewModel() {

    private val _tasks = MutableLiveData<MutableList<TodoTask>>(mutableListOf())
    val tasks: LiveData<MutableList<TodoTask>> = _tasks

    fun addNewTask(newTask: TodoTask) {
        newTask.id = generateId()
        _tasks.value?.add(newTask)
    }

    fun getById(id: Long): TodoTask? {
        var filtered = (_tasks.value)!!.filter { e -> e.id == id }
        if (filtered.isEmpty()) {
            return null
        } else {
            return filtered[0]
        }
    }

    fun generateId(): Long {
        var id = (1..1000).random().toLong()
        while (getById(id) != null) {
            id = (1..1000).random().toLong()
        }
        return id
    }

    fun save(newTask: TodoTask) {
        _tasks.value?.find { it.id == newTask.id }?.taskTitle = newTask.taskTitle
    }

    fun removeAt(position: Int) {
        _tasks.value?.removeAt(position)
    }

}