package com.example.ui_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.ui_app.DataApplication.Companion.prefs
import com.example.ui_app.databinding.FragmentMainDataBinding
import com.example.ui_app.databinding.FragmentManagerBinding


class MainDataFragment : Fragment(R.layout.fragment_main_data) {

    private lateinit var binding: FragmentMainDataBinding
    private var idBoss:Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showIdManager()
        arguments?.let {
            idBoss = it.getInt("idManager")
        }
    }

    private fun showIdManager() {
        Toast.makeText(requireContext(),idBoss.toString(),Toast.LENGTH_SHORT).show()
    }


}