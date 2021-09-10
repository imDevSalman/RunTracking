package com.sonicmaster.runningapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sonicmaster.runningapp.R
import com.sonicmaster.runningapp.databinding.FragmentSetupBinding

class SetupFragment : Fragment() {

    lateinit var binding: FragmentSetupBinding

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
                findNavController().navigate(R.id.action_setupFragment_to_runFragment)
            }
        }

    }
}