package com.example.courseworkapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseworkapp.R
import com.example.courseworkapp.data.RoomStyle
import com.example.courseworkapp.ui.RoomAdapter

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val roomStyles = listOf(
            RoomStyle(
                background = R.drawable.bg_room_yellow,
                vectorDrawable = R.drawable.rb_moon_yellow,
                vectorPaddingTop = 0,
                textColor = R.color.elemYellow,
            ),
            RoomStyle(
                background = R.drawable.bg_room_yellow,
                vectorDrawable = R.drawable.rb_wave_yellow,
                vectorPaddingTop = 0,
                textColor = R.color.elemYellow,
            ),
            RoomStyle(
                background = R.drawable.bg_room_yellow,
                vectorDrawable = R.drawable.rb_flower_yllow,
                vectorPaddingTop = 10,
                textColor = R.color.elemYellow,
            ),
            RoomStyle(
                background = R.drawable.bg_room_red,
                vectorDrawable = R.drawable.rb_moon_red,
                vectorPaddingTop = 0,
                textColor = R.color.elemRed,
            ),
            RoomStyle(
                background = R.drawable.bg_room_red,
                vectorDrawable = R.drawable.rb_wave_red,
                vectorPaddingTop = 0,
                textColor = R.color.elemRed,
            ),
            RoomStyle(
                background = R.drawable.bg_room_red,
                vectorDrawable = R.drawable.rb_flower_red,
                vectorPaddingTop = 10,
                textColor = R.color.elemRed,
            ),
            RoomStyle(
                background = R.drawable.bg_room_green,
                vectorDrawable = R.drawable.rb_moon_green,
                vectorPaddingTop = 0,
                textColor = R.color.elemGreen,
            ),
            RoomStyle(
                background = R.drawable.bg_room_green,
                vectorDrawable = R.drawable.rb_wave_green,
                vectorPaddingTop = 0,
                textColor = R.color.elemGreen,
            ),
            RoomStyle(
                background = R.drawable.bg_room_green,
                vectorDrawable = R.drawable.rb_flower_green,
                vectorPaddingTop = 10,
                textColor = R.color.elemGreen,
            ),
            RoomStyle(
                background = R.drawable.bg_room_violet,
                vectorDrawable = R.drawable.rb_moon_violet,
                vectorPaddingTop = 0,
                textColor = R.color.elemViolet,
            ),
            RoomStyle(
                background = R.drawable.bg_room_violet,
                vectorDrawable = R.drawable.rb_wave_violet,
                vectorPaddingTop = 0,
                textColor = R.color.elemViolet,
            ),
            RoomStyle(
                background = R.drawable.bg_room_violet,
                vectorDrawable = R.drawable.rb_flower_violet,
                vectorPaddingTop = 10,
                textColor = R.color.elemViolet,
            ),
        )

        val roomAdapter = RoomAdapter(roomStyles)

        val recyclerView = findViewById<RecyclerView>(R.id.roomsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = roomAdapter
    }
}
