package com.example.api

import com.example.model.UserProfile
import com.example.model.UserProfileRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.GET

interface UserProfileService {
    @POST("/api/profile/create")
    fun createUserProfile(@Body userProfileRequest: UserProfileRequest): Call<Void> // No need for suspend here if using Call

    @PUT("/api/profile/update")
    suspend fun updateUserProfile(@Body userProfileRequest: UserProfileRequest): Call<Void>
    @GET("/api/profile/me")
    suspend fun getProfile(): Response<UserProfile>
}
