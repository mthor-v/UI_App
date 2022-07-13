package com.example.ui_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ui_app.DataApplication.Companion.prefs
import com.example.ui_app.databinding.FragmentHomeBinding
import com.example.ui_app.databinding.FragmentSignUpBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.btnFormCreate.setOnClickListener { goToCreate() }
        binding.btnLogout.setOnClickListener { logout() }
    }

    private fun goToCreate() {
        findNavController().navigate(R.id.action_homeFragment_to_formByCiaFragment)
    }

    private fun logout() {
        prefs.wipe()
        findNavController().navigate(R.id.action_homeFragment_to_welcomeFragment)
    }
}