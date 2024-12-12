package com.example.courseworkapp.ui.adapters

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
import com.example.courseworkapp.utils.RoomStyleManager

class RoomAdapter(
    private val isHome: Boolean,
    private val isCreatedRooms: Boolean
) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    private val rooms = mutableListOf<Room>()

    // Листенеры для кнопок
    var onCopyCodeClick: ((String) -> Unit)? = null
    var onOpenRoomClick: ((String, String, String) -> Unit)? = null
    var onChangeCodeClick: ((String) -> Unit)? = null
    var onChangeRoomNameClick: ((String, String) -> Unit)? = null


    fun updateRooms(newRooms: List<Room>) {

        rooms.clear()
        rooms.addAll(newRooms)
        notifyDataSetChanged()
    }

    inner class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val room = view.findViewById<View>(R.id.viewItemRoom)
        val imageViewBG = view.findViewById<ImageView>(R.id.imageViewBG)
        val roomTitle = view.findViewById<TextView>(R.id.roomTitle)
        val joinButton = view.findViewById<Button>(R.id.buttonOpenRoom)
        val textRoomCode = view.findViewById<TextView>(R.id.textRoomCode)
        val editCodeButton = view.findViewById<ImageButton>(R.id.buttonChangeCode)
        val editRoomButton = view.findViewById<ImageButton>(R.id.buttonChangeRoom)
        val textNumberOfParticipants = view.findViewById<TextView>(R.id.NumberofPeople)
        val copyCodeButton = view.findViewById<ImageButton>(R.id.buttonCopyCode)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_room, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {

        val room = rooms[position]  // Получаем данные комнаты
        val style = RoomStyleManager.getRoomSlyle(room.id) // Выбор стиля из списка
        val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams

        // Устанавливаем данные комнаты
        holder.roomTitle.text = room.name
        holder.textRoomCode.text = room.connectionCode
        holder.textNumberOfParticipants.text = room.participants.size.toString()

        // Устанавливаем обработчики событий
        holder.copyCodeButton.setOnClickListener {
            onCopyCodeClick?.invoke(room.connectionCode)
        }

        holder.joinButton.setOnClickListener {
            onOpenRoomClick?.invoke(room.id, room.name, room.connectionCode)
        }

        holder.editCodeButton.setOnClickListener {
            onChangeCodeClick?.invoke(room.id)
        }

        holder.editRoomButton.setOnClickListener {
            onChangeRoomNameClick?.invoke(room.id, room.name)
        }

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