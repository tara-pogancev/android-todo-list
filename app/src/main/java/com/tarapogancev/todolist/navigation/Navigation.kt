package com.tarapogancev.todolist.navigation

import androidx.navigation.NavController
import com.tarapogancev.todolist.R

class Navigation(navController: NavController) {

    var navController = navController

    fun listToNewTask() {
        navController.navigate(R.id.action_todoListFragment_to_addNewTodo)
    }

    fun newTaskToList() {
        navController.navigate(R.id.action_addNewTodo_to_todoListFragment)
    }


}