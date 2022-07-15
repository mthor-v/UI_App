package com.example.ui_app

import com.example.ui_app.DataApplication.Companion.prefs
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    val accessTkn: String
        get() = prefs.getAccessToken()

    @GET("users/")
    fun getUserData(@Query("code") code:Int): Call<UserResponseDTO>

    @POST("login/")
    fun getTokenUser(@Body dataUser: CredentialsDTO): Call<TokenResponseDTO>

    @POST("signup/")
    fun createUser(@Body dataUser: DataSignupDTO): Call<TokenResponseDTO>

    @POST("manager/")
    fun createManager(@Header("Authorization") auth:String ,@Body dataManager: DataManagerDTO): Call<ManagerIdDTO>

    @POST("refresh/")
    fun getNewAccessToken(@Body refreshToken: RefreshTokenDTO): Call<AccessTokenResponseDTO>

}