package com.sonicmaster.runningapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sonicmaster.runningapp.R
import com.sonicmaster.runningapp.databinding.FragmentSetupBinding
import com.sonicmaster.runningapp.utils.Utility.KEY_FIRST_TIME_TOGGLE
import com.sonicmaster.runningapp.utils.Utility.KEY_NAME
import com.sonicmaster.runningapp.utils.Utility.KEY_WEIGHT
import com.sonicmaster.runningapp.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class SetupFragment : Fragment() {

    lateinit var binding: FragmentSetupBinding

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @set:Inject
    var firstLaunch by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!firstLaunch) {
            findNavController().navigate(
                R.id.action_setupFragment_to_runFragment,
                savedInstanceState
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            continueBtn.isEnabled = false
            weight.addTextChangedListener {
                val name = name.text.toString().trim()
                continueBtn.isEnabled = name.isNotEmpty() && it.toString().isNotEmpty()
            }

            continueBtn.setOnClickListener {
                val success = saveUserInfo()
                if (success) {
                    findNavController().navigate(R.id.action_setupFragment_to_runFragment)
                } else {
                    requireView().snackbar("Please enter all fields")
                }
            }
        }
    }

    private fun saveUserInfo(): Boolean {
        var name: String
        var weight: String

        binding.apply {
            name = this.name.text.toString()
            weight = this.weight.text.toString()

            if (name.isEmpty() || weight.isEmpty()) {
                return false
            }

            sharedPreferences.edit().apply {
                putString(KEY_NAME, name)
                putFloat(KEY_WEIGHT, weight.toFloat())
                putBoolean(KEY_FIRST_TIME_TOGGLE, false)
            }.apply()

            return true
        }
    }
}