package com.tarapogancev.todolist.navigation

import android.os.Bundle
import androidx.navigation.NavController
import com.tarapogancev.todolist.R
import com.tarapogancev.todolist.model.TodoTask

class Navigation(navController: NavController) {

    var navController = navController

    fun listToNewTask() {
        navController.navigate(R.id.action_todoListFragment_to_addNewTodoFragment)
    }

    fun goBack() {
        navController.navigateUp()
    }

    fun listToEditTask(task: TodoTask) {
        var bundle = Bundle()
        bundle.putSerializable("object", task)
        navController.navigate(R.id.action_todoListFragment_to_editTodoFragment, bundle)
    }

}