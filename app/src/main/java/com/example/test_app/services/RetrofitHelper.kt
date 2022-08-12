package com.example.test_app.services

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitHelper {
    private val url: String = "http://10.20.22.173:8080/"

    fun getInstance(): Retrofit {
        val client = OkHttpClient.Builder().addInterceptor { chain: Interceptor.Chain ->
            val token: String = "toke"
            val newRequest: Request = chain.request().newBuilder()
//                .addHeader("Authorization", "Bearer $token")
                .addHeader("Content-Type","application/json")
                .addHeader("Accept","*/*")
                .build()
            chain.proceed(newRequest)
        }.build()

        return Retrofit
            .Builder()
//            .client(client)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}