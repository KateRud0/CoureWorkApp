package com.example.courseworkapp.ui

import android.os.Bundle
import android.provider.Settings.Global.putString
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

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
