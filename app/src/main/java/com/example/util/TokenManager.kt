package com.example.util

import android.content.Context

class TokenManager(context: Context) {

    private val sharedPref = context.getSharedPreferences("APP_PREF", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        with(sharedPref.edit()) {
            putString("TOKEN", token)
            apply()
        }
    }

    fun getToken(): String? {
        return sharedPref.getString("TOKEN", null)
    }

    fun clearToken() {
        with(sharedPref.edit()) {
            remove("TOKEN")
            apply()
        }
    }
}