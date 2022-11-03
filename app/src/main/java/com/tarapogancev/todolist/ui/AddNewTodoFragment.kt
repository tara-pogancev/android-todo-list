package com.tarapogancev.todolist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.tarapogancev.todolist.MainActivity
import com.tarapogancev.todolist.databinding.FragmentAddNewTodoBinding
import com.tarapogancev.todolist.model.TodoTask
import com.tarapogancev.todolist.navigation.Navigation
import com.tarapogancev.todolist.viewModel.TodoViewModel


class AddNewTodoFragment : Fragment() {

    private var binding: FragmentAddNewTodoBinding? = null

    private lateinit var navigation: Navigation

    private val sharedViewModel: TodoViewModel by activityViewModels()

    var task = TodoTask(0, "", false)
    private var navigationArgs : TodoTask? = null

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

        navigationArgs = arguments?.getSerializable("object") as TodoTask?

        binding?.apply {

            if (navigationArgs == null) {
                // NEW TASK
                newTask = task
                buttonSave.setOnClickListener {
                    task.taskTitle = editTextTaskTitle.text.toString()
                    sharedViewModel.addNewTask(task)
                    navigation.goBack()
                }
            } else {
                // EDIT TASK
                editTextTaskTitle.setText(navigationArgs?.taskTitle)
                buttonSave.setOnClickListener {
                    navigationArgs?.taskTitle = editTextTaskTitle.text.toString()
                    sharedViewModel.save(task)
                    navigation.goBack()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}