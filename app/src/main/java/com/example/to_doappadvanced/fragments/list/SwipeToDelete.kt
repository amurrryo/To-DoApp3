package com.example.to_doappadvanced.fragments.list

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator

abstract class SwipeToDelete: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        recyclerView.itemAnimator = SlideInLeftAnimator().apply {
            addDuration = 500
            removeDuration = 500
            changeDuration = 10
        }
        return false
    }
}