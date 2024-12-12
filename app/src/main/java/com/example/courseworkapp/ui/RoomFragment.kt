package com.example.courseworkapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.courseworkapp.R
import com.example.courseworkapp.ui.adapters.RoomPagerAdapter


class RoomFragment : Fragment(R.layout.fragment_room) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    private lateinit var viewPager: ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val roomId = arguments?.getString("roomId") ?: return
        val roomName = arguments?.getString("roomName")
        val connectionCode = arguments?.getString("connectionCode")
        val isOwner = arguments?.getBoolean("isOwner") ?: false

        val text = view.findViewById<TextView>(R.id.textViewRoomName)
        text.text = roomName

        val text2 = view.findViewById<TextView>(R.id.textViewRoomCodeRoom)
        text2.text = connectionCode

        viewPager = view.findViewById(R.id.viewPager)

        // Настройка адаптера для ViewPager2
        val adapter = RoomPagerAdapter(this, roomId, isOwner)
        viewPager.adapter = adapter

        // Настройка кнопок переключения страниц
        val buttonTasks = view.findViewById<Button>(R.id.buttonTasks)
        val buttonParticipants = view.findViewById<Button>(R.id.buttonParticipants)

        buttonTasks.setOnClickListener {
            viewPager.setCurrentItem(0, true) // Переключение на первую страницу
            buttonTasks.setBackgroundResource(R.drawable.bg_button_room_1)
            buttonParticipants.setBackgroundResource(R.drawable.bg_button_room_2)

        }

        buttonParticipants.setOnClickListener {
            viewPager.setCurrentItem(1, true) // Переключение на вторую страницу
            buttonTasks.setBackgroundResource(R.drawable.bg_button_room_2)
            buttonParticipants.setBackgroundResource(R.drawable.bg_button_room_1)
        }

        val goBack = view.findViewById<ImageButton>(R.id.buttonGoBackRoom)

        goBack.setOnClickListener {
            findNavController().popBackStack()
        }


    }

}