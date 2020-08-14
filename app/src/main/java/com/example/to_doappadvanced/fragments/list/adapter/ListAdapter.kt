package com.example.to_doappadvanced.fragments.list.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.to_doappadvanced.data.models.ToDoData
import com.example.to_doappadvanced.databinding.RowLayoutBinding
import com.example.to_doappadvanced.fragments.list.ListFragmentDirections
import kotlinx.android.synthetic.main.row_layout.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<ToDoData>()

    class MyViewHolder(private val binding: RowLayoutBinding): RecyclerView.ViewHolder(binding.root)  {

        fun bind(toDoData: ToDoData) {
            binding.toDoData = toDoData
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutBinding.inflate(layoutInflater)
                return MyViewHolder(
                    binding
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(
            parent
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = dataList[position]
        holder.itemView.complete_task_btn.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToSaveFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)
        }

        holder.bind(currentItem)
    }

    fun setData(toDoData: List<ToDoData>) {
        val toDoDiffUtil = ToDoDiffUtil(dataList, toDoData)
        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
        this.dataList = toDoData
        toDoDiffResult.dispatchUpdatesTo(this)
    }
}