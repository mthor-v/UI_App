package com.example.ui_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.ui_app.databinding.FragmentWelcomeBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWelcomeBinding.bind(view)
        binding.btnIntro.setOnClickListener { sendRequest() }
    }

    private fun sendRequest() {
        val code = binding.etCode.text.toString().toInt()
        requestLogin(code)
    }

    private fun requestLogin(userCode: Int){
        val api = DataApplication.retrofit.create(APIService::class.java)
        val petition: Call<UserResponseDTO> = api.getUserData(userCode)
        return petition.enqueue(
            object : Callback<UserResponseDTO> {
                override fun onResponse(
                    call: Call<UserResponseDTO>,
                    response: Response<UserResponseDTO>
                ) {
                    val rsp: UserResponseDTO? = response.body()
                    if(rsp != null){
                        val gson = Gson()
                        val jsonString = gson.toJson(rsp)
                        val obj = JSONObject(jsonString)
                        // showAlert(obj.getString("name"))
                        goToLogin(obj.getInt("code"),obj.getString("name"))
                    }else{
                        goToSignup(binding.etCode.text.toString().toInt())
                    }
                }
                override fun onFailure(call: Call<UserResponseDTO>, t: Throwable) {
                    showAlert(t.toString())
                }
            }
        )
    }

    private fun goToSignup(usrCode: Int) {
        val bundle = bundleOf("code" to usrCode)
        findNavController().navigate(R.id.action_welcomeFragment_to_signUpFragment, bundle)
    }

    private fun goToLogin(usrCode:Int, usrName:String) {
        val bundle = bundleOf("code" to usrCode, "name" to usrName)
        findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment, bundle)
    }

    private fun showAlert(message:String){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Mensaje")
        builder.setMessage(message)
        val dialog = builder.create()
        dialog.show()
    }

}