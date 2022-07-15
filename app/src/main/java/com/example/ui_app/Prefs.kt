package com.example.ui_app

import android.content.Context

class Prefs(context: Context) {

    val SHARED_NAME = "MyDB"
    val SHARED_ACCESS = "Access"
    val SHARED_REFRESH = "Refresh"
    val SHARED_MANAGER_ID = "id"
    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun saveAccessToken(accessT:String){
        storage.edit().putString(SHARED_ACCESS,accessT).apply()
    }
    fun saveRefreshToken(refreshT:String){
        storage.edit().putString(SHARED_REFRESH,refreshT).apply()
    }
    fun getAccessToken():String{
        return storage.getString(SHARED_ACCESS,"")!!
    }
    fun getRefreshToken():String{
        return storage.getString(SHARED_REFRESH,"")!!
    }
//    fun saveIdManager(idBoss:Int){
//        storage.edit().putInt(SHARED_MANAGER_ID, idBoss).apply()
//    }
//    fun getIdManager():Int{
//        return storage.getInt(SHARED_MANAGER_ID,0)
//    }


    fun wipe(){
        storage.edit().clear().apply()
    }
}