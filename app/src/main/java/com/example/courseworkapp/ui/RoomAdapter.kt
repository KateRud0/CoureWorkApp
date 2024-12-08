package com.example.courseworkapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.courseworkapp.R
import com.example.courseworkapp.data.RoomStyle

class RoomAdapter(private val roomStyles: List<RoomStyle>) :
    RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    inner class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val room = view.findViewById<View>(R.id.viewItemRoom)
        val imageViewBG = view.findViewById<ImageView>(R.id.imageViewBG)
        val roomTitle = view.findViewById<TextView>(R.id.roomTitle)
        val joinButton = view.findViewById<Button>(R.id.joinButton)
        val textRoomCode = view.findViewById<TextView>(R.id.textRoomCode)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_room, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val style = roomStyles.random() // Выбор стиля из списка
        val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams

        layoutParams.width = dpToPx(290, holder.itemView.context) // Ширина комнаты
        layoutParams.height = dpToPx(160, holder.itemView.context) // Высота комнаты
        layoutParams.marginEnd = dpToPx(8, holder.itemView.context) // Отступ между комнатами
        holder.itemView.layoutParams = layoutParams

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

        holder.roomTitle.text = "Комната ${position + 1}" // Пример названия
        holder.textRoomCode.text = "#${(100000..999999).random()}" // Пример кода комнаты
    }

    override fun getItemCount(): Int = 10 // Количество тестовых комнат

    private fun dpToPx(dp: Int, context: Context): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }
}