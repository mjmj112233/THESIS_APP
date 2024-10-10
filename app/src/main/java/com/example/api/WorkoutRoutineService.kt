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
}


