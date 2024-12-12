package com.example.courseworkapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.courseworkapp.R
import com.example.courseworkapp.data.Task

class TaskAdapter(
    private val isOwner: Boolean,
    private val onTaskClick: (Task) -> Unit,
    private val onDeleteClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val tasks = mutableListOf<Task>()

    fun updateTasks(newTasks: List<Task>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskName: TextView = view.findViewById(R.id.textViewTaskName)
        val taskDescription: TextView = view.findViewById(R.id.textViewTaskDescription)
        val buttonOpen: Button = view.findViewById(R.id.buttonOpenTask)
        val buttonDelete: ImageButton = view.findViewById(R.id.buttonDeleteTask)
        val taskStatus: TextView = view.findViewById(R.id.textViewTaskStatus)
        val bgImage: ImageView = view.findViewById(R.id.imageViewBGTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.taskName.text = task.name
        holder.taskDescription.text = task.description
        holder.taskStatus.text = task.status

        var bgShape : Int
        var statusColor : Int
        var bgVector : Int
        when (task.status) {

            "Активно" -> {
                bgShape = R.drawable.bg_room_green
                statusColor = R.color.taskActive
                bgVector = R.drawable.tb_green
            }
            "Завершено" -> {
                bgShape = R.drawable.bg_room_red
                statusColor = R.color.taskCompleted
                bgVector = R.drawable.tb_red
            }else -> {
                    bgShape = R.drawable.bg_room_violet
                    statusColor = R.color.taskCreated
                    bgVector = R.drawable.tb_violet
            }
        }
        holder.itemView.setBackgroundResource(bgShape)
        holder.bgImage.setImageResource(bgVector)
        holder.taskStatus.setTextColor(
            ContextCompat.getColor(holder.itemView.context,statusColor))

        // Обработка кнопок
        holder.buttonOpen.setOnClickListener { onTaskClick(task) }
        if (isOwner) {
            holder.buttonDelete.visibility = View.VISIBLE
            holder.buttonDelete.setOnClickListener { onDeleteClick(task) }
        } else {
            holder.buttonDelete.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = tasks.size
}
