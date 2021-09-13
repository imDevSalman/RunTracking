package com.sonicmaster.runningapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.sonicmaster.runningapp.R
import com.sonicmaster.runningapp.databinding.FragmentSettingsBinding
import com.sonicmaster.runningapp.utils.Utility.KEY_NAME
import com.sonicmaster.runningapp.utils.Utility.KEY_WEIGHT
import com.sonicmaster.runningapp.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserInfo()
        binding.btnApplyChanges.setOnClickListener {
            if (updateUserInfo()) {
                requireView().snackbar("Data updated!")
            } else {
                requireView().snackbar("Please enter all fields")
            }
        }
    }

    private fun loadUserInfo() {
        val name = sharedPreferences.getString(KEY_NAME, "")
        val weight = sharedPreferences.getFloat(KEY_WEIGHT, 80f).toString()
        binding.apply {
            etName.setText(name)
            etWeight.setText(weight)
        }
    }

    private fun updateUserInfo(): Boolean {
        var nameText: String
        var weightText: String

        binding.apply {
            nameText = etName.text.toString()
            weightText = etWeight.text.toString()

            if (nameText.isEmpty() || weightText.isEmpty()) {
                return false
            }

            sharedPreferences.edit().apply {
                putString(KEY_NAME, nameText)
                putFloat(KEY_WEIGHT, weightText.toFloat())
            }.apply()
        }
        return true
    }
}