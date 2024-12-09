package com.example.courseworkapp.ui

import android.content.Context
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
import com.example.courseworkapp.data.Room
import com.example.courseworkapp.data.RoomStyle

class RoomAdapter(
    private val isHome: Boolean,
    private val isCreatedRooms: Boolean
) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

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
    private val rooms = mutableListOf<Room>()

    fun setRooms(newRooms: List<Room>) {
        rooms.clear()
        rooms.addAll(newRooms)
        notifyDataSetChanged()
    }

    fun updateRooms(newRooms: List<Room>) {
        rooms.clear()
        rooms.addAll(newRooms)
        notifyDataSetChanged()
    }

    inner class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val room = view.findViewById<View>(R.id.viewItemRoom)
        val imageViewBG = view.findViewById<ImageView>(R.id.imageViewBG)
        val roomTitle = view.findViewById<TextView>(R.id.roomTitle)
        val joinButton = view.findViewById<Button>(R.id.joinButton)
        val textRoomCode = view.findViewById<TextView>(R.id.textRoomCode)
        val editCodeButton = view.findViewById<ImageButton>(R.id.buttonChangeCode)
        val editRoomButton = view.findViewById<ImageButton>(R.id.buttonChangeRoom)
        val textNumberOfParticipants = view.findViewById<TextView>(R.id.NumberofPeople)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_room, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val style = roomStyles.random() // Выбор стиля из списка
        val room = rooms[position]  // Получаем данные комнаты
        val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams

        // Устанавливаем данные комнаты
        holder.roomTitle.text = room.name
        holder.textRoomCode.text = room.connectionCode
        holder.textNumberOfParticipants.text = room.participants.size.toString()

        if (isHome) {
            layoutParams.width = dpToPx(290, holder.itemView.context) // Ширина комнаты
            layoutParams.height = dpToPx(160, holder.itemView.context) // Высота комнаты
            layoutParams.marginEnd = dpToPx(8, holder.itemView.context) // Отступ между комнатами
        } else {
            layoutParams.bottomMargin = dpToPx(8, holder.itemView.context) // Отступ между комнатами
        }

        if (isCreatedRooms) {
            holder.editCodeButton.visibility = View.VISIBLE
            holder.editRoomButton.visibility = View.VISIBLE
        } else {
            holder.editCodeButton.visibility = View.GONE
            holder.editRoomButton.visibility = View.GONE
        }

        holder.room.setBackgroundResource(style.background)
        holder.imageViewBG.setImageResource(style.vectorDrawable)
        holder.imageViewBG.setPadding(
            0,
            style.vectorPaddingTop,
            dpToPx(-15, holder.itemView.context),
            0
        )
        holder.textRoomCode.setTextColor(
            ContextCompat.getColor(holder.itemView.context, style.textColor)
        )
        holder.joinButton.setTextColor(
            ContextCompat.getColor(holder.itemView.context, style.textColor)
        )

        holder.itemView.layoutParams = layoutParams
    }

    override fun getItemCount(): Int = rooms.size

    private fun dpToPx(dp: Int, context: Context): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }
}