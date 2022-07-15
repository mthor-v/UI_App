package com.example.ui_app

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.ui_app.DataApplication.Companion.prefs
import com.example.ui_app.DataApplication.Companion.refresh
import com.example.ui_app.databinding.FragmentManagerBinding
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ManagerFragment : Fragment(R.layout.fragment_manager) {

    private lateinit var binding: FragmentManagerBinding
    private  lateinit var bossData: DataManagerDTO
    private val accessTkn:String = "Bearer ${prefs.getAccessToken()}"
    private val options = arrayOf("Primaria", "Secundaria", "Técnico", "Tecnologo", "Pregrado", "Postgrado")
    private var defaultPosition = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentManagerBinding.bind(view)
        val name:String = binding.etBossName.text.toString()
        val surename:String = binding.etBossSurname.text.toString()
        val email:String = binding.etBossEmail.text.toString()
        val grade:String = binding.btnBossEducation.text.toString()
        bossData = DataManagerDTO(name, surename, email, grade)
        binding.btnBossEducation.setOnClickListener { selectGrade() }
        binding.btnSend.setOnClickListener { createBoss(accessTkn, bossData) }
    }

    private fun createBoss(tkn:String, dataBoss:DataManagerDTO) {
        val api = DataApplication.retrofit.create(APIService::class.java)
        val petition: Call<ManagerIdDTO> = api.createManager(tkn, dataBoss)
        return petition.enqueue(
            object : Callback<ManagerIdDTO>{
                override fun onResponse(
                    call: Call<ManagerIdDTO>,
                    response: Response<ManagerIdDTO>
                ) {
                    val cod: Int = response.code()
                    if(cod == 201){
                        val rsp: ManagerIdDTO? = response.body()
                        val gson = Gson()
                        val jsonString = gson.toJson(rsp)
                        val obj = JSONObject(jsonString)
                        val idManager = obj.getString("id").toInt()
                        goToMainData(idManager)
                    }else if(cod == 401){
                        println(accessTkn)
                        showAlert("Token vencido desea seguir?")
                        // Debe haber un metodo que le de refresh al token
                        refresh
                    }else{
                        showAlert(response.message())
                    }
                }
                override fun onFailure(call: Call<ManagerIdDTO>, t: Throwable) {
                    showAlert("Algo salio mal")
                    println(t.toString())
                }

            }
        )
    }

    private fun goToMainData(idManager:Int) {
        val bundle = bundleOf("id" to idManager)
        findNavController().navigate(R.id.action_managerFragment_to_mainDataFragment, bundle)
    }

    private fun selectGrade() {
        val builderSingle = AlertDialog.Builder(requireContext())
        builderSingle.setTitle("Selecciona el grado")
        builderSingle.setPositiveButton(getString(android.R.string.ok)) {
                dialog, _ -> dialog.dismiss()
        }
        builderSingle.setSingleChoiceItems(options,defaultPosition){ dialog, which ->
            defaultPosition = which
            binding.btnBossEducation.text = options[which]
        }
        builderSingle.show()
    }

    private fun showAlert(message:String) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Mensaje")
        builder.setMessage(message)
        val dialog = builder.create()
        dialog.show()
    }
}