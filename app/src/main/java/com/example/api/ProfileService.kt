package com.example.api

import com.example.model.UserProfileRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Header

interface ProfileService {

    @POST("api/profile/update")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Body userProfileRequest: UserProfileRequest
    ): Call<Void>
}