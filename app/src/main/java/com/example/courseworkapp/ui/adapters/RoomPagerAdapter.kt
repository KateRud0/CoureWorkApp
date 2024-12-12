package com.example.courseworkapp.ui.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.courseworkapp.ui.ParticipantsFragment
import com.example.courseworkapp.ui.TasksFragment

class RoomPagerAdapter(
    fragment: Fragment,
    private val roomId: String,
    private val isOwner: Boolean) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> TasksFragment()
            1 -> ParticipantsFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
        fragment.arguments = Bundle().apply {
            putString("roomId", roomId)
            putBoolean("isOwner", isOwner)
        }
        return fragment
    }
}
