package com.tarapogancev.todolist.ui

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.tarapogancev.todolist.BaseApplication
import com.tarapogancev.todolist.MainActivity
import com.tarapogancev.todolist.databinding.FragmentAddNewTodoBinding
import com.tarapogancev.todolist.model.TodoTask
import com.tarapogancev.todolist.navigation.Navigation
import com.tarapogancev.todolist.viewModel.TodoViewModel
import com.tarapogancev.todolist.viewModel.TodoViewModelFactory


class AddNewTodoFragment : Fragment() {

    private var binding: FragmentAddNewTodoBinding? = null

    private lateinit var navigation: Navigation

    private val sharedViewModel: TodoViewModel by activityViewModels {
        TodoViewModelFactory(
            (activity?.application as BaseApplication).database.todoTaskDao()
        )
    }

    var task = TodoTask(0, "", "", false)

    private var navigationArgs: TodoTask? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navigation = (activity as MainActivity).navController
        binding = FragmentAddNewTodoBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            newTask = task
            buttonSave.setOnClickListener {
                task.taskTitle = editTextTaskTitle.text.toString().trim()
                task.description = editTextTaskDescription.text.toString().trim()
                sharedViewModel.addNewTask(task)
                navigation.goBack()
            }

            buttonSave.isEnabled = false
            editTextTaskTitle.addTextChangedListener {
                buttonSave.isEnabled = editTextTaskTitle.text.toString().trim().isNotEmpty()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}