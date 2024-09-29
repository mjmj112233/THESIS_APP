package com.example.repository

import com.example.api.RetroFitInstance
import com.example.model.User
import retrofit2.Call

class AuthRepository {

    fun registerUser(user: User): Call<User> {
        return RetrofitInstance.authService.registerUser(user)
    }

    fun loginUser(user: User): Call<LoginResponse> {
        return RetrofitInstance.authService.loginUser(user)
    }
}