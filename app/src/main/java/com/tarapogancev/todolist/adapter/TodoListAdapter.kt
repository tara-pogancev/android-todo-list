package com.tarapogancev.todolist.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.tarapogancev.todolist.R
import com.tarapogancev.todolist.dao.TodoTaskDao
import com.tarapogancev.todolist.model.TodoTask
import com.tarapogancev.todolist.navigation.Navigation
import com.tarapogancev.todolist.viewModel.TodoViewModel

class TodoListAdapter(private val context: Context, private val navigation: Navigation, private val viewModel: TodoViewModel)
    : RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>() {

    var tasks: MutableList<TodoTask> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return TodoListViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.taskTitle.text = tasks[position].taskTitle

        if (position % 2 == 0) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.lightBlue))
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }

        holder.checkbox.isChecked = tasks[position].isFinished

        holder.checkbox.setOnClickListener {
            viewModel.checkUncheckTask(tasks[position])
            holder.checkbox.isChecked = tasks[position].isFinished
        }

        holder.cardView.setOnClickListener {
            navigation.listToEditTask(tasks[position])
        }
    }

    fun addItem(task: TodoTask, position: Int) {
        tasks.add(position, task)
    }

    fun removeItem(position: Int) {
        viewModel.removeAt(position)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    class TodoListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        var taskTitle: TextView = view.findViewById(R.id.text_taskTitle)
        var cardView: MaterialCardView = view.findViewById(R.id.cardView_listItem)
        var checkbox: CheckBox = view.findViewById(R.id.checkbox)
    }

}