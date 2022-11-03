package com.tarapogancev.todolist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tarapogancev.todolist.MainActivity
import com.tarapogancev.todolist.R
import com.tarapogancev.todolist.adapter.TodoListAdapter
import com.tarapogancev.todolist.databinding.FragmentTodoListBinding
import com.tarapogancev.todolist.navigation.Navigation
import com.tarapogancev.todolist.viewModel.TodoViewModel


class TodoListFragment : Fragment() {

    private var binding: FragmentTodoListBinding? = null

    private lateinit var navigation: Navigation

    private val sharedViewModel: TodoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navigation = (activity as MainActivity).navController
        val fragmentBinding = FragmentTodoListBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TodoListAdapter(requireContext(), sharedViewModel.tasks.value!!, navigation)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner

            buttonAddNewTask.setOnClickListener {
                navigation.listToNewTask()
            }

            if (sharedViewModel.tasks.value?.size == 0) {
                textNoTasksYet.visibility = View.VISIBLE
            } else {
                textNoTasksYet.visibility = View.INVISIBLE
            }

            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(false)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}