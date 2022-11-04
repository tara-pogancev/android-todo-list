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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.tarapogancev.todolist.R
import com.tarapogancev.todolist.model.TodoTask
import com.tarapogancev.todolist.navigation.Navigation

class TodoListAdapter(private val context: Context, private val data: MutableList<TodoTask>, private val navigation: Navigation)
    :RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>() {

    class TodoListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        var taskTitle: TextView = view.findViewById(R.id.text_taskTitle)
        var cardView: MaterialCardView = view.findViewById(R.id.cardView_listItem)
        var checkbox: CheckBox = view.findViewById(R.id.checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return TodoListViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.taskTitle.text = data[position].taskTitle

        if (position % 2 == 0) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.lightBlue))
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }

        holder.checkbox.isChecked = data[position].isFinished

        holder.checkbox.setOnClickListener {
            data[position].isFinished = !data[position].isFinished
            holder.checkbox.isChecked = data[position].isFinished
        }

        holder.cardView.setOnClickListener {
            navigation.listToEditTask(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}