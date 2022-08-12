package com.example.test_app.services

import com.example.test_app.data.models.ApiResult
import com.example.test_app.data.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    fun onLogin(@Body user: User):Call<ApiResult>
}