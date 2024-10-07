package com.example.repository

import android.content.Context
import com.example.api.RetrofitInstance
import com.example.model.LoginRequest
import com.example.model.LoginResponse
import com.example.model.RegistrationRequest
import retrofit2.Call

class AuthRepository(private val context: Context) {

    fun registerUser(user: RegistrationRequest): Call<Void> {
        // Use context to initialize authService dynamically
        return RetrofitInstance.AuthService(context).registerUser(user)
    }

    fun loginUser(user: LoginRequest): Call<LoginResponse> {
        // Use context to initialize authService dynamically
        return RetrofitInstance.AuthService(context).loginUser(user)
    }
}
