package com.example.courseworkapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.courseworkapp.R
import com.example.courseworkapp.data.Task
import com.example.courseworkapp.viewmodel.RoomViewModel
import java.util.UUID

class AddEditTasksFragment: Fragment(R.layout.fragment_add_edit_task) {

    private lateinit var roomViewModel: RoomViewModel
    private var taskId: String? = null
    private var roomId: String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskId = arguments?.getString("taskId")
        roomId = arguments?.getString("roomId")
        val currentRoomId = roomId ?: return

        val buttonGoBack = view.findViewById<ImageButton>(R.id.buttonGoBackTask)
        val buttonSaveTask = view.findViewById<Button>(R.id.buttonSaveTask)

        val editTextTaskName = view.findViewById<EditText>(R.id.editTextTaskName)
        val editTextTaskDescription = view.findViewById<EditText>(R.id.editTextTaskDescription)
        val editTextNumberOfPeople = view.findViewById<EditText>(R.id.editTextNumberOfPeople)
        val textViewStatus = view.findViewById<TextView>(R.id.textViewStatus)
        val textViewAnswers = view.findViewById<TextView>(R.id.textViewAnswers)

        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)

        // Если taskId передан, то это редактирование задания
        if (taskId != null) {
            roomViewModel.getTaskDetails(taskId!!).observe(viewLifecycleOwner) { task ->

                editTextTaskName.setText(task.name)
                editTextTaskDescription.setText(task.description)
                editTextNumberOfPeople.setText(task.maxGroupSize.toString())
                textViewStatus.text = task.status
                textViewAnswers.text = task.answerForm

            }
        }

        // Обработка кнопки "Сохранить"
        buttonSaveTask.setOnClickListener {
            val name = editTextTaskName.text.toString().trim()
            val description = editTextTaskDescription.text.toString().trim()
            val maxGroupSize = editTextNumberOfPeople.text.toString().toIntOrNull() ?: 0
            val status = textViewStatus.text.toString().trim()

            if (name.isBlank() || description.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Все поля должны быть заполнены",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                val newTask = Task(
                    id = taskId ?: UUID.randomUUID().toString(),
                    name = name,
                    description = description,
                    maxGroupSize = maxGroupSize,
                    status = status,
                    answerForm = "выбор этапа"
                )

                roomViewModel.saveTask(currentRoomId, newTask)
                findNavController().popBackStack()
            }
        }

        buttonGoBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

}
