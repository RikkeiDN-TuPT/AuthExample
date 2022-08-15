package com.example.test_app.data.repository

import com.example.test_app.data.models.ApiResult
import com.example.test_app.data.models.User
import com.example.test_app.services.AuthService
import com.example.test_app.services.RetrofitHelper
import com.example.test_app.utils.Constant
import retrofit2.Call
import retrofit2.Retrofit

class AuthRepository {
    private var retrofitClient: Retrofit = RetrofitHelper().getInstance(Constant.base_url)

    fun onLogin(user: User): Call<ApiResult> {
        val requestService = retrofitClient.create(AuthService::class.java)
        return requestService.onLogin(user)
    }

    fun onRegister(user: User): Call<ApiResult> {
        val requestService = retrofitClient.create(AuthService::class.java)
        return requestService.onRegister(user)
    }

}


