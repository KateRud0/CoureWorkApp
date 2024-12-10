package com.example.courseworkapp.utils

import com.example.courseworkapp.R
import com.example.courseworkapp.data.RoomStyle

class RoomStyleManager {
    companion object{
        private val roomStyles = listOf(
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

        private val roomStyleMap = mutableMapOf<String, RoomStyle>()

        fun getRoomSlyle ( id : String ): RoomStyle {
            return roomStyleMap.getOrPut(id) {
                roomStyles.random() // Если нет, выбираем рандомный стиль и сохраняем
            }
        }
    }

}