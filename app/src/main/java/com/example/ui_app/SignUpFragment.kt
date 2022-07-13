package com.example.ui_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.ui_app.DataApplication.Companion.prefs
import com.example.ui_app.databinding.FragmentSignUpBinding
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding
    lateinit var userDataSignup: DataSignupDTO
    private var code:Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)
        arguments?.let{
            code = it.getInt("code")
        }
        binding.etCode.hint = code.toString()
        binding.btnCreate.setOnClickListener { userSignUp() }
    }

    private fun userSignUp() {
        val name:String = binding.etName.text.toString()
        val surname:String = binding.etSurname.text.toString()
        val email:String = binding.etEmail.text.toString()
        val pwd:String = binding.etPassword.text.toString()
        val role:String = "Usuario"
        if(name.isEmpty()){
            toast("Se requiere ingresar nombre")
        }else if (surname.isEmpty()){
            toast("Se requiere ingresar apellido")
        }else if (binding.etCode.text.toString().isEmpty()){
            toast("Se requiere ingresar código")
        }else if (email.isEmpty()){
            toast("Se requiere ingresar email")
        }else if (binding.etCode.text.toString().isEmpty()){
            toast("Se requiere ingresar telefono")
        }else if (pwd.isEmpty()){
            toast("Se requiere ingresar contraseña")
        }else{
            val usrCode = binding.etCode.text.toString().toInt()
            val phone = binding.etPhone.text.toString().toInt()
            userDataSignup = DataSignupDTO(usrCode,pwd,name,surname,email,phone,role)
            requestSignup(userDataSignup)
        }
    }

    private fun requestSignup(usrCred: DataSignupDTO){
        val api = DataApplication.retrofit.create(APIService::class.java)
        val petition: Call<TokenResponseDTO> = api.createUser(usrCred)
        return petition.enqueue(
            object : Callback<TokenResponseDTO> {
                override fun onResponse(
                    call: Call<TokenResponseDTO>,
                    response: Response<TokenResponseDTO>
                ) { val cod: Int = response.code()
                    if(cod == 200){
                        val rsp: TokenResponseDTO? = response.body()
                        val gson = Gson()
                        val jsonString = gson.toJson(rsp)
                        val obj = JSONObject(jsonString)
                        prefs.wipe()
                        prefs.saveAccessToken(obj.getString("access"))
                        prefs.saveRefreshToken(obj.getString("refresh"))
                        showAlert(prefs.getAccessToken())
                        findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
                    }else if(cod == 400){
                        showAlert(response.message() + ": Código o Email ya están registrados")
                    }
                }
                override fun onFailure(call: Call<TokenResponseDTO>, t: Throwable) {
                    showAlert("Ha ocurrido un error")
                    println(t.toString())
                }
            }
        )
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