package com.example.taskmaster.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmaster.R
import com.example.taskmaster.databinding.TaskViewLayoutBinding
import com.example.taskmaster.models.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TaskRVVBListAdapter(
    private val deleteUpdateCallback : (type: String, position: Int, task: Task) -> Unit
):
    ListAdapter<Task,TaskRVVBListAdapter.ViewHolder>(DiffCallback()){

    class ViewHolder(val taskRecyclerViewAdapter: TaskViewLayoutBinding):
        RecyclerView.ViewHolder(taskRecyclerViewAdapter.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TaskViewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = getItem(position)

        holder.taskRecyclerViewAdapter.titleTxt.text = task.title
        holder.taskRecyclerViewAdapter.descrTxt.text = task.description

        val dateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a", Locale.getDefault())

        holder.taskRecyclerViewAdapter.dateTxt.text = dateFormat.format(task.date)

        holder.taskRecyclerViewAdapter.deleteImg.setOnClickListener{
            if (holder.adapterPosition != -1){
                deleteUpdateCallback("delete",holder.adapterPosition, task)
            }
        }

        holder.taskRecyclerViewAdapter.editImg.setOnClickListener{
            if (holder.adapterPosition != -1){
                deleteUpdateCallback("update",holder.adapterPosition, task)
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }
}