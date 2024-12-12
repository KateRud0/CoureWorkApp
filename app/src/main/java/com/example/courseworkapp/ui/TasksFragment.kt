package com.example.courseworkapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseworkapp.R
import com.example.courseworkapp.ui.adapters.TaskAdapter
import com.example.courseworkapp.ui.dialogs.ConfirmDeleteDialog
import com.example.courseworkapp.viewmodel.RoomViewModel


class TasksFragment : Fragment(R.layout.fragment_tasks) {

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var roomViewModel: RoomViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewParticipants)
        val isOwner = arguments?.getBoolean("isOwner") ?: false
        val roomId = arguments?.getString("roomId") ?: return

        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)

        val addTask = view.findViewById<Button>(R.id.buttonAddTask)

        if (isOwner) {
            addTask.visibility = View.VISIBLE
        } else {
            addTask.visibility = View.GONE
        }

        // Инициализация адаптера
        taskAdapter = TaskAdapter(
            isOwner = isOwner,
            onTaskClick = { task ->
                val action = R.id.action_room_to_task
                val bundle = Bundle().apply {
                    putString("taskId", task.id)
                    putString("roomId", roomId)
                    putString("taskName", task.name)
                    putString("taskDescription", task.description)
                    putBoolean("isOwner", true)
                }
                findNavController().navigate(action, bundle)
            },
            onDeleteClick = { task ->
                val dialog = ConfirmDeleteDialog {
                    roomViewModel.deleteTask(task.id, roomId)
                }
                dialog.show(parentFragmentManager, "ConfirmDeleteDialog")
            }
        )
        // Настройка RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = taskAdapter

        // Загрузка задач
        val roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        roomViewModel.getTasks(roomId).observe(viewLifecycleOwner) { tasks ->
            taskAdapter.updateTasks(tasks)
        }


        addTask.setOnClickListener {
            val action = R.id.action_room_to_addEditTasks
            val bundle = Bundle().apply {
                putString("roomId", roomId)
            }
            findNavController().navigate(action, bundle)
        }
    }
}