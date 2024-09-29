package com.example.api

import com.example.model.User
import com.example.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/api/auth/register")
    fun registerUser(@Body user: User): Call<User>

    @POST("/api/auth/login")
    fun loginUser(@Body user: User): Call<LoginResponse>
}