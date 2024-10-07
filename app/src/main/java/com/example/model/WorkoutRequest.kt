package com.example.model

data class WorkoutRequest(
    val id: Long,
    val name: String,
    val description: String,
    val equipment: String,
    val classification: WorkoutClassificationRequest,
    val demoUrl: String?,
    val imageUrl: String?
)
