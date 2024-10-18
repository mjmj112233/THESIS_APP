package com.example.model

data class WorkoutRoutineRequest(
    val id: Long = 0,
    val user: UserProfileRequest, // Reference to the UserProfile
    val workoutInfo: WorkoutInfoRequest? = null, // Optional reference to WorkoutInfo
    val dayNum: Int,
    val isRestDay: Boolean,
    val isFinished: Boolean = false, // New field to track if the workout is finished
    val userProfile: UserProfileRequest
)


