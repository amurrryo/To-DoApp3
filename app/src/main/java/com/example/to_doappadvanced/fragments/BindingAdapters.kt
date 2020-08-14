package com.example.to_doappadvanced.fragments

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.to_doappadvanced.R
import com.example.to_doappadvanced.data.models.Priority
import com.example.to_doappadvanced.data.models.ToDoData
import com.example.to_doappadvanced.fragments.list.ListFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.row_layout.view.*

class BindingAdapters {
    companion object{

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if(navigate){
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:sendDataToSaveData")
        @JvmStatic
        fun sendDataToSaveData(view: ConstraintLayout, currentItem: ToDoData) {
            view.setOnLongClickListener{
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                view.findNavController().navigate(action)
                return@setOnLongClickListener true
            }
        }
        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
            when(emptyDatabase.value) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority){
            when(priority){
                Priority.HIGH -> {view.setSelection(0)}
                Priority.MEDIUM -> {view.setSelection(1)}
                Priority.LOW -> {view.setSelection(2)}
                Priority.NONE -> {view.setSelection(3)}

            }
        }

        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(cardView: CardView, priority: Priority) {
            when (priority){
                Priority.HIGH -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.red))}
                Priority.MEDIUM -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.yellow))}
                Priority.LOW -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.green))}
                Priority.NONE -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.darkGray))}
            }
        }

        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view: ConstraintLayout, currentItem: ToDoData) {
            view.setOnLongClickListener{
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                view.findNavController().navigate(action)
                return@setOnLongClickListener true
            }
         }
    }
}