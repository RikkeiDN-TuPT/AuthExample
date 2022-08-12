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

    fun onLogin(user: User): MutableLiveData<ApiResult> {
        var data = MutableLiveData<ApiResult>();
        val requestService = retrofitClient.create(AuthService::class.java)
        val request: Call<ApiResult> = requestService.onLogin(user)
        request.enqueue(object : Callback<ApiResult?> {
            override fun onResponse(
                call: Call<ApiResult?>?,
                response: Response<ApiResult?>
            ) {
                data.value = response.body()
            }
            override fun onFailure(call: Call<ApiResult?>?, t: Throwable?) {
                data.value?.success = false
                data.value?.message = t?.message
                data.value?.data = null
            }
        })
        return data
    }
}


