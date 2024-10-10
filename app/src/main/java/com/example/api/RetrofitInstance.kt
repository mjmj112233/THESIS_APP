package com.example.api

import android.content.Context
import com.example.util.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

object RetrofitInstance {

    private const val BASE_URL = "http://192.168.254.135:8080/"  // Replace with your Spring Boot server URL

    // Function to provide an OkHttpClient that adds JWT tokens to headers and logs HTTP requests/responses
    private fun provideOkHttpClient(context: Context): OkHttpClient {
        // Create the logging interceptor
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Log the request and response body
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Add logging interceptor
            .addInterceptor { chain ->
                val token = TokenManager.getToken(context)  // Fetch token from TokenManager
                val requestBuilder = chain.request().newBuilder()

                // If token exists, add it to the Authorization header
                if (token != null) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }

                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    // Function to provide Retrofit instance with the token-aware OkHttpClient
    private fun provideRetrofit(context: Context): Retrofit {
        // Create a Gson instance with lenient parsing
        val gson = GsonBuilder()
            .setLenient() // Set Gson to lenient mode
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient(context))  // Attach the OkHttpClient with the token
            .addConverterFactory(GsonConverterFactory.create(gson)) // Use the custom Gson instance
            .build()
    }

    // To use AuthService and UserProfileService in your code
    fun AuthService(context: Context): AuthService {
        return provideRetrofit(context).create(AuthService::class.java)
    }

    fun UserProfileService(context: Context): UserProfileService {
        return provideRetrofit(context).create(UserProfileService::class.java)
    }

    fun WorkoutRoutineService(context: Context): WorkoutRoutineService {
        return provideRetrofit(context).create(WorkoutRoutineService::class.java)
    }

}
