package com.example.ui_app

class ResponseModel {

}

data class TokenResponseDTO(
    val refresh: String?,
    val access: String?
)

data class CredentialsDTO(
    val code: Int?, val password: String?
)

data class UserResponseDTO(
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

data class DataManagerDTO(
    val name: String,
    val surname: String,
    val email: String,
    val academic_level: String
)

data class ManagerIdDTO(val id:Int)

data class RefreshTokenDTO(val refresh: String?)

data class AccessTokenResponseDTO(val access: String?)