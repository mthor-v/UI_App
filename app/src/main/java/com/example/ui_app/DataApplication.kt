package com.example.ui_app

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataApplication: Application () {

    companion object{
        lateinit var prefs: Prefs
        lateinit var retrofit: Retrofit
    }
    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
        retrofit = objRetrofit()
    }

    private fun objRetrofit(): Retrofit{
        val gson:Gson = GsonBuilder().create()
        val gsonFactory = GsonConverterFactory.create(gson)
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(gsonFactory)
            .build()
    }
}