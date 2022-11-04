package com.tarapogancev.todolist.ui

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tarapogancev.todolist.R
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


abstract class SwipeToDeleteCallback(context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val context = context

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            .addSwipeLeftBackgroundColor(ContextCompat.getColor(context, R.color.red))
            .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
            .addSwipeRightBackgroundColor(ContextCompat.getColor(context, R.color.green))
            .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
            .create()
            .decorate()

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

}