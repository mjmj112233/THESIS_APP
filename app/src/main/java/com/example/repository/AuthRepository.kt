package com.example.repository


import com.example.api.RetrofitInstance
import com.example.model.LoginRequest
import com.example.model.LoginResponse
import com.example.model.RegistrationRequest
import retrofit2.Call

class AuthRepository {

    fun registerUser(user: RegistrationRequest): Call<Void> {
        return RetrofitInstance.authService.registerUser(user)
    }

    fun loginUser(user: LoginRequest): Call<LoginResponse> {
        return RetrofitInstance.authService.loginUser(user)
    }
}