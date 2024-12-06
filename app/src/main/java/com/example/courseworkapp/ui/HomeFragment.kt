package com.example.courseworkapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.courseworkapp.R
import androidx.navigation.fragment.findNavController


class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settingsButton = view.findViewById<ImageButton>(R.id.buttonSettings)

        settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_settings)
        }

    }


}