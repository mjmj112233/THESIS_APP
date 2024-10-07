package com.example.model

data class WorkoutRoutineRequest(
    val id: Long = 0,
    val user: UserProfileRequest, // Reference to the UserProfile
    val workoutInfo: WorkoutInfoRequest? = null, // Optional reference to WorkoutInfo
    val dayNum: Int,
    val isRestDay: Boolean,
    val userProfile: UserProfileRequest
)
