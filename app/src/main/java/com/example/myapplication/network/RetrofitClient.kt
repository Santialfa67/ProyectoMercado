package com.example.myapplication.network

import com.example.myapplication.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    //private const val BASE_URL = "https://opvbnmzsbvoieelqdogr.supabase.co" // Reemplaza con la URL base de tu Supabase API (ej: https://<your-project-id>.supabase.co/rest/v1/)
    private const val BASE_URL = "http://10.0.2.2:8080/" // Cambiar a localhost si estás usando Spring Boot local

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}