package com.example.ui_app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.ui_app.DataApplication.Companion.prefs
import com.example.ui_app.databinding.FragmentLoginBinding
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var userCredentialsDTO:CredentialsDTO
    private var code:Int? = null
    private var name:String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        arguments?.let {
            code = it.getInt("code")
            name = it.getString("name")
        }
        greetingUser()
        binding.btnLogin.setOnClickListener { userLogin() }
    }

    private fun userLogin() {
        if(binding.etCode.text.toString().isEmpty()){
            toast("Se requiere ingresar código")
        }else if (binding.etPassword.text.toString().isEmpty()){
            toast("Se requiere ingresar contraseña")
        }else{
            val code:Int = binding.etCode.text.toString().toInt()
            val pwd:String = binding.etPassword.text.toString()
            userCredentialsDTO = CredentialsDTO(code,pwd)
            requestLogin(userCredentialsDTO)
        }
    }

    private fun requestLogin(usrCred: CredentialsDTO){
        val api = DataApplication.retrofit.create(APIService::class.java)
        val petition: Call<TokenResponseDTO> = api.getTokenUser(usrCred)
        return petition.enqueue(
            object : Callback<TokenResponseDTO> {
                override fun onResponse(
                    call: Call<TokenResponseDTO>,
                    response: Response<TokenResponseDTO>
                ) {
                    val rsp: TokenResponseDTO? = response.body()
                    if(rsp != null){
                        val gson = Gson()
                        val jsonString = gson.toJson(rsp)
                        val obj = JSONObject(jsonString)
                        prefs.wipe()
                        prefs.saveAccessToken(obj.getString("access"))
                        prefs.saveRefreshToken(obj.getString("refresh"))
                        showAlert(prefs.getAccessToken())
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }else{
                        showAlert("Credenciales Incorrectas")
                    }
                }
                override fun onFailure(call: Call<TokenResponseDTO>, t: Throwable) {
                    showAlert(t.toString())
                }
            }
        )
    }

    @SuppressLint("SetTextI18n")
    private fun greetingUser() {
        binding.tvGreeting.text = "¡Hola ${name}!"
        binding.etCode.hint = code.toString()
    }
    private fun showAlert(message:String){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Mensaje")
        builder.setMessage(message)
        val dialog = builder.create()
        dialog.show()
    }
    private fun toast(msg:String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}