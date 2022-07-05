package com.example.ui_app

class ResponseModel {

}

class TokenResponseDTO(
    val refresh: String?,
    val access: String?
)

class CredentialsDTO(
    val code: Int?, val password: String?
)

class UserResponseDTO(
    val code: Int,
    val name: String,
    val surname: String,
    val email: String,
    val phone: Int,
    val role: String
)

data class DataSignupDTO(
    val code: Int,
    val password: String?,
    val name: String,
    val surname: String,
    val email: String,
    val phone: Int,
    val role: String
)