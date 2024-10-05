package com.example.api

import com.example.model.LoginRequest
import com.example.model.LoginResponse
import com.example.model.RegistrationRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/auth/register")
    fun registerUser(@Body registrationRequest: RegistrationRequest): Call<Void>

    @POST("api/auth/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>
}