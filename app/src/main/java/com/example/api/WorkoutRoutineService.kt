package com.example.api

import com.example.model.UserProfileRequest
import com.example.model.WorkoutRoutineRequest
import com.example.model.WorkoutRoutineResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface WorkoutRoutineService {
    @POST("/api/workout-routine/generate")
    suspend fun generateWorkoutRoutine(): Map<String, List<WorkoutRoutineRequest>>

    @PUT("/api/workout-routine/update")
    suspend fun updateWorkoutRoutine(): Response<Map<String, List<WorkoutRoutineRequest>>>

    @GET("/api/workout-routine/my-routines")
    suspend fun getWorkoutRoutines(): List<WorkoutRoutineRequest>

    // New API: Reset all workouts for the user
    @PUT("/api/workout-routine/reset-all")
    suspend fun resetAllWorkouts(): Response<String>

    // New API: Reset a specific workout by ID
    @PUT("/api/workout-routine/reset/{id}")
    suspend fun resetWorkout(@Path("id") id: Long): Response<String>

    @PUT("/api/workout-routine/mark-finished/{id}")
    suspend fun markWorkoutAsFinished(@Path("id") id: Long): Response<Void>
}



