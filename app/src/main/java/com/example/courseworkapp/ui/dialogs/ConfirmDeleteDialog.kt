package com.example.courseworkapp.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.courseworkapp.R

class ConfirmDeleteDialog(
    private val onConfirm: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm_delete, null)

        val buttonConfirm = view.findViewById<Button>(R.id.buttonConfirm)
        val buttonCancel = view.findViewById<Button>(R.id.buttonCancel)

        buttonConfirm.setOnClickListener {
            onConfirm()
            dismiss()
        }

        buttonCancel.setOnClickListener {
            dismiss()
        }

        builder.setView(view)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }
}
