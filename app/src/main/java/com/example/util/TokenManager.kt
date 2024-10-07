package com.example.util

import android.content.Context

object TokenManager {

    private const val PREFS_NAME = "auth_prefs"
    private const val JWT_TOKEN_KEY = "jwt_token"

    // Save the token
    fun saveToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(JWT_TOKEN_KEY, token)
            apply()  // Commit the changes asynchronously
        }
    }

    // Get the token
    fun getToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(JWT_TOKEN_KEY, null)
    }

    // Clear the token (e.g., during logout)
    fun clearToken(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove(JWT_TOKEN_KEY)
            apply()
        }
    }
}