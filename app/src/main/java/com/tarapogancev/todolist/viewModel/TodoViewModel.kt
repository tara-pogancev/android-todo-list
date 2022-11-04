package com.tarapogancev.todolist.viewModel

import androidx.lifecycle.*
import com.tarapogancev.todolist.dao.TodoTaskDao
import com.tarapogancev.todolist.model.TodoTask
import kotlinx.coroutines.launch

class TodoViewModel(private val todoTaskDao: TodoTaskDao) : ViewModel() {

    val tasks: LiveData<List<TodoTask>> = todoTaskDao.getTasks().asLiveData()

    fun addNewTask(newTask: TodoTask) {
        viewModelScope.launch {
            todoTaskDao.insert(newTask)
        }
    }

    fun save(newTask: TodoTask) {
        viewModelScope.launch {
            todoTaskDao.update(newTask)
        }
    }

    fun removeAt(position: Int) {
        val taskToDelete = tasks.value?.get(position)
        viewModelScope.launch {
            if (taskToDelete != null) {
                todoTaskDao.delete(taskToDelete)
            }
        }
    }

    fun checkUncheckTask(task: TodoTask) {
        task.isFinished = !task.isFinished
        viewModelScope.launch {
            save(task)
        }
    }

    fun getAllTasks(): List<TodoTask> {
        return tasks.value ?: listOf();
    }

    fun getTaskListSize(): Int {
        return getAllTasks().size
    }

}

class TodoViewModelFactory (private val todoTaskDao: TodoTaskDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(todoTaskDao) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}