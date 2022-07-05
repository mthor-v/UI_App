package com.example.ui_app

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @GET("users/")
    fun getUserData(@Query("code") code:Int): Call<UserResponseDTO>

    @POST("login/")
    fun getTokenUser(@Body dataUser: CredentialsDTO): Call<TokenResponseDTO>

    @POST("signup/")
    fun createUser(@Body dataUser: DataSignupDTO): Call<TokenResponseDTO>
}