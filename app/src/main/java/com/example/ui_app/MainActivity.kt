package com.example.ui_app


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ui_app.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //private lateinit var userCredentialsDTO: CredentialsDTO

//    companion object{
//        lateinit var retrofit: Retrofit
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding.btnLogin.setOnClickListener { sendInfo() }
    }

//    private fun sendInfo() {
//        val code:Int = binding.etCode.text.toString().toInt()
//        val pwd:String = binding.etPassword.text.toString()
//        userCredentialsDTO = CredentialsDTO(code,pwd)
//        requestLogin(userCredentialsDTO)
//
//    }
//
    private fun objRetrofit(): Retrofit{
        val gson:Gson = GsonBuilder().create()
        val gsonFactory = GsonConverterFactory.create(gson)
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(gsonFactory)
            .build()
    }
//
//    private fun requestLogin(usrCred: CredentialsDTO){
//        val api = objRetrofit().create(APIService::class.java)
//        val petition: Call<TokenResponseDTO> = api.getTokenUser(usrCred)
//        return petition.enqueue(
//            object : Callback<TokenResponseDTO>{
//                override fun onResponse(
//                    call: Call<TokenResponseDTO>,
//                    response: Response<TokenResponseDTO>
//                ) {
//                    val rsp: TokenResponseDTO? = response.body()
//                    if(rsp != null){
//                        val gson = Gson()
//                        val jsonString = gson.toJson(rsp)
//                        val obj = JSONObject(jsonString)
//                        prefs.saveAccessToken(obj.getString("access"))
//                        prefs.saveRefreshToken(obj.getString("refresh"))
//                        showAlert(prefs.getAccessToken())
//                    }else{
//                        showAlert("Null response")
//                    }
//
//                }
//
//                override fun onFailure(call: Call<TokenResponseDTO>, t: Throwable) {
//                    showAlert(t.toString())
//                }
//            }
//        )
//    }
//    private fun showAlert(message:String){
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Mensaje")
//        builder.setMessage(message)
//        val dialog = builder.create()
//        dialog.show()
//    }
//
//    private fun toast() {
//        Toast.makeText(this, "Call active", Toast.LENGTH_SHORT).show()
//    }
}