package com.sonicmaster.runningapp.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sonicmaster.runningapp.R

class CancelDialog : DialogFragment() {
    private var clickListener: (() -> Unit)? = null

    fun setClickListener(listener: () -> Unit) {
        clickListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(
            requireContext()
        ).setTitle("Cancel the Run?")
            .setMessage("Are you sure you want to cancel the run and delete all its data?")
            .setIcon(R.drawable.ic_baseline_delete_24)
            .setPositiveButton("Yes") { _, _ ->
                clickListener?.let {
                    it()
                }
            }
            .setNegativeButton("No") { dialogInterface, _ -> dialogInterface.cancel() }.create()
    }

}