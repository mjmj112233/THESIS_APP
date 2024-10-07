package com.example.api

import com.example.model.UserProfileRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Header
import retrofit2.http.PUT

interface ProfileService {

    @POST("api/profile/create")
    fun createUserProfile(
        @Header("Authorization") token: String,
        @Body userProfileRequest: UserProfileRequest
    ): Call<Void>  // No changes needed

    @PUT("api/profile/update")
    fun updateUserProfile(
        @Header("Authorization") token: String,
        @Body userProfileRequest: UserProfileRequest
    ): Call<Void>  // No changes needed
}
