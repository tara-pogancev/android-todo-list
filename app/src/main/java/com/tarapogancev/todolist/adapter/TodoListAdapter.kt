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
    : ListAdapter<TodoTask, TodoListAdapter.TodoListViewHolder>(DiffCallback) {

    class TodoListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        var taskTitle: TextView = view.findViewById(R.id.text_taskTitle)
        var cardView: MaterialCardView = view.findViewById(R.id.cardView_listItem)
        var checkbox: CheckBox = view.findViewById(R.id.checkbox)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<TodoTask>() {
        override fun areItemsTheSame(oldItem: TodoTask, newItem: TodoTask): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TodoTask, newItem: TodoTask): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return TodoListViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.taskTitle.text = getItem(position).taskTitle

        if (position % 2 == 0) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.lightBlue))
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }

        holder.checkbox.isChecked = getItem(position).isFinished

        holder.checkbox.setOnClickListener {
            viewModel.checkUncheckTask(getItem(position))
            holder.checkbox.isChecked = getItem(position).isFinished
        }

        holder.cardView.setOnClickListener {
            navigation.listToEditTask(getItem(position))
        }
    }

    // TODO
    fun addItem(task: TodoTask) {

    }

    // TODO
    fun removeItem(position: Int) {
        viewModel.removeAt(position)
    }

}