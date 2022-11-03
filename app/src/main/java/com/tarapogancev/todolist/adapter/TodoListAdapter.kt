package com.tarapogancev.todolist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.tarapogancev.todolist.R
import com.tarapogancev.todolist.model.TodoTask

class TodoListAdapter(private val context: Context, private val data: LiveData<MutableList<TodoTask>>)
    :RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>() {

    class TodoListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        var taskTitle: TextView = view.findViewById(R.id.text_taskTitle)
        var cardView: MaterialCardView = view.findViewById(R.id.cardView_listItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return TodoListViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        var task = data.value?.get(position)
        holder.taskTitle.text = task?.taskTitle

        if (position % 2 == 0) {
            holder.cardView.setBackgroundColor(context.resources.getColor(R.color.primaryLightColor))
        }
    }

    override fun getItemCount(): Int {
        return data.value?.size ?: 0
    }


}