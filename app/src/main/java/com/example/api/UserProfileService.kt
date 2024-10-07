package com.example.api

import com.example.model.UserProfileRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserProfileService {
    @POST("/api/profile/create")
    fun createUserProfile(@Body userProfileRequest: UserProfileRequest): Call<Void> // No need for suspend here if using Call

    @PUT("/api/profile/update")
    suspend fun updateUserProfile(@Body profileRequest: UserProfileRequest): Call<Void>
}
