package com.example.ui_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ui_app.databinding.FragmentFormByCiaBinding


class FormByCiaFragment : Fragment(R.layout.fragment_form_by_cia) {

    private lateinit var binding: FragmentFormByCiaBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFormByCiaBinding.bind(view)
        binding.btnHotels.setOnClickListener { goToBoss() }
    }

    private fun goToBoss() {
        findNavController().navigate(R.id.action_formByCiaFragment_to_managerFragment)
    }
}