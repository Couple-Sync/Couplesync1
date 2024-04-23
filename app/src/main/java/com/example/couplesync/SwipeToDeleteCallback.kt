package com.example.couplesync

import android.graphics.Color
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDeleteCallback(private val adapter: RestaurantsAdapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false // We don't want to handle drag and drop
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        when (direction) {
            ItemTouchHelper.LEFT -> {
                // Swipe left action
                viewHolder.itemView.setBackgroundColor(Color.RED)
//                adapter.removeItem(position)
            }
            ItemTouchHelper.RIGHT -> {
                // Swipe right action
                viewHolder.itemView.setBackgroundColor(Color.GREEN)
//                adapter.removeItem(position)
            }
        }
    }
}
