package com.example.ui_app

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataApplication: Application () {

    companion object{
        lateinit var prefs: Prefs
        lateinit var retrofit: Retrofit
        lateinit var refresh: Unit
        lateinit var refreshTkn: RefreshTokenDTO
    }
    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
        retrofit = objRetrofit()
        refreshTkn = RefreshTokenDTO(prefs.getRefreshToken())
        refresh = requestAccess(refreshTkn)
    }

//    private val client = OkHttpClient.Builder().apply {
//        addInterceptor(MyInterceptor())
//    }.build()

    private fun objRetrofit(): Retrofit{
        val gson:Gson = GsonBuilder().create()
        val gsonFactory = GsonConverterFactory.create(gson)
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
//            .client(client)
            .addConverterFactory(gsonFactory)
            .build()
    }

    private fun requestAccess(refreshTkn:RefreshTokenDTO){
        val api = retrofit.create(APIService::class.java)
        val petition: Call<AccessTokenResponseDTO> = api.getNewAccessToken(refreshTkn)
        return petition.enqueue(
            object : Callback<AccessTokenResponseDTO> {
                override fun onResponse(
                    call: Call<AccessTokenResponseDTO>,
                    response: Response<AccessTokenResponseDTO>
                ) {
                    val rsp: AccessTokenResponseDTO? = response.body()
                    if(rsp != null){
                        val gson = Gson()
                        val jsonString = gson.toJson(rsp)
                        val obj = JSONObject(jsonString)
                        prefs.saveAccessToken(obj.getString("access"))
                    }else{
                        println(response.code().toString() + response.message())
                    }
                }
                override fun onFailure(call: Call<AccessTokenResponseDTO>, t: Throwable) {
                    println(t.toString())
                }
            }
        )
    }
}