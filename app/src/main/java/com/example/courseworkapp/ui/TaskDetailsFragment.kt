package com.example.courseworkapp.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.courseworkapp.R

class TaskDetailsFragment : Fragment(R.layout.fragment_task_details) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isOwner = arguments?.getBoolean("isOwner")
        val taskId = arguments?.getString("taskId")
        val taskName = arguments?.getString("taskName")
        val taskDescription = arguments?.getString("taskDescription")
        val roomId = arguments?.getString("roomId") ?: return

        val textViewTaskName = view.findViewById<TextView>(R.id.textViewTaskNameTask)
        val textViewTaskDescription = view.findViewById<TextView>(R.id.textViewTaskDescriptionTask)

        textViewTaskName.text = taskName
        textViewTaskDescription.text = taskDescription

    }
}