package com.tarapogancev.todolist.ui

import android.content.Context.VIBRATOR_SERVICE
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tarapogancev.todolist.BaseApplication
import com.tarapogancev.todolist.MainActivity
import com.tarapogancev.todolist.adapter.TodoListAdapter
import com.tarapogancev.todolist.databinding.FragmentTodoListBinding
import com.tarapogancev.todolist.model.TodoTask
import com.tarapogancev.todolist.navigation.Navigation
import com.tarapogancev.todolist.viewModel.TodoViewModel
import com.tarapogancev.todolist.viewModel.TodoViewModelFactory

const val SHORT_VIBRATION_DURATION = 100L

class TodoListFragment : Fragment() {

    private var binding: FragmentTodoListBinding? = null

    private lateinit var navigation: Navigation

    private val sharedViewModel: TodoViewModel by activityViewModels {
        TodoViewModelFactory(
            (activity?.application as BaseApplication).database.todoTaskDao()
        )
    }

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

        val vibration = context?.getSystemService(VIBRATOR_SERVICE) as Vibrator?

        val adapter = TodoListAdapter(requireContext(), navigation, sharedViewModel)

        sharedViewModel.tasks.observe(this.viewLifecycleOwner) { tasks ->
            tasks.let {
                adapter.tasks = tasks.toMutableList()
            }
             adapter.notifyItemRangeChanged(0, tasks.size)
            setNoTasksTextVisibility(binding!!.textNoTasksYet, adapter)
            Log.e("LOG", "List Changed! Size: ${tasks.size}")
        }

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner

            buttonAddNewTask.setOnClickListener {
                navigation.listToNewTask()
            }

            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(false)

            val swipeToDeleteCallback = object : SwipeToDeleteCallback(requireContext()) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    when (direction) {

                        ItemTouchHelper.LEFT -> {
                            val position = viewHolder.adapterPosition
                            val deletedTask = sharedViewModel.tasks.value!![position]
                            sharedViewModel.removeAt(position)
                            adapter.removeItem(position)
                            adapter.notifyItemRemoved(position)
                            vibration?.vibrate(SHORT_VIBRATION_DURATION)

                            Snackbar.make(recyclerView, deletedTask.taskTitle, Snackbar.LENGTH_LONG)
                                .setAction("Undo") {
                                    sharedViewModel.addNewTask(deletedTask)
                                    adapter.addItem(deletedTask, position)
                                    adapter.notifyItemInserted(position)
                                }.show()
                        }

                        ItemTouchHelper.RIGHT -> {
                            val position = viewHolder.adapterPosition
                            navigation.listToEditTask(sharedViewModel.tasks.value!![position])
                            vibration?.vibrate(SHORT_VIBRATION_DURATION)

                        }
                    }
                }
            }

            val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView)

            setNoTasksTextVisibility(textNoTasksYet, adapter)
        }
    }

    fun setNoTasksTextVisibility(textNoTasksYet: TextView, adapter: TodoListAdapter) {
            if (adapter.tasks.size == 0) {
                textNoTasksYet.visibility = View.VISIBLE
            } else {
                textNoTasksYet.visibility = View.INVISIBLE
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}