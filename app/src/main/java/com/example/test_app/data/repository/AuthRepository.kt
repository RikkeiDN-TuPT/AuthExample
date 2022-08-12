package com.example.test_app.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.test_app.data.models.ApiResult
import com.example.test_app.data.models.User
import com.example.test_app.services.AuthService
import com.example.test_app.services.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class AuthRepository {
    var retrofitClient: Retrofit = RetrofitHelper().getInstance()

    fun onLogin(user: User): Call<ApiResult> {
        val requestService = retrofitClient.create(AuthService::class.java)
        return requestService.onLogin(user)
    }
}


