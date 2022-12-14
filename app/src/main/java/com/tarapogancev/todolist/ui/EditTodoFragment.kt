package com.tarapogancev.todolist.ui

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.tarapogancev.todolist.BaseApplication
import com.tarapogancev.todolist.MainActivity
import com.tarapogancev.todolist.R
import com.tarapogancev.todolist.databinding.FragmentAddNewTodoBinding
import com.tarapogancev.todolist.databinding.FragmentEditTodoBinding
import com.tarapogancev.todolist.model.TodoTask
import com.tarapogancev.todolist.navigation.Navigation
import com.tarapogancev.todolist.viewModel.TodoViewModel
import com.tarapogancev.todolist.viewModel.TodoViewModelFactory


class EditTodoFragment : Fragment() {

    private var binding: FragmentEditTodoBinding? = null

    private lateinit var navigation: Navigation

    private val sharedViewModel: TodoViewModel by activityViewModels {
        TodoViewModelFactory(
            (activity?.application as BaseApplication).database.todoTaskDao()
        )
    }

    private var navigationArgs: TodoTask? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navigation = (activity as MainActivity).navController
        navigation = (activity as MainActivity).navController
        binding = FragmentEditTodoBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationArgs = arguments?.getSerializable("object") as TodoTask?

        binding?.apply {
            editTextTaskTitle.setText(navigationArgs?.taskTitle)
            editTextTaskDescription.setText(navigationArgs?.description)
            checkboxIsFinished.isChecked = navigationArgs?.isFinished ?: false

            buttonSave.setOnClickListener {
                navigationArgs?.taskTitle = editTextTaskTitle.text.toString().trim()
                navigationArgs?.description = editTextTaskDescription.text.toString().trim()
                navigationArgs?.isFinished = checkboxIsFinished.isChecked
                sharedViewModel.save(navigationArgs!!)
                navigation.goBack()
            }

            editTextTaskTitle.setOnKeyListener(object :View.OnKeyListener {
                override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                    buttonSave.isEnabled = editTextTaskTitle.text.toString().trim().isNotEmpty()
                    return true
                }
            } )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}