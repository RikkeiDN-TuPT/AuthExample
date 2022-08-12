package com.example.test_app.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test_app.data.models.ApiResult
import com.example.test_app.data.models.AuthState
import com.example.test_app.data.models.User
import com.example.test_app.data.repository.AuthRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private var authRepository: AuthRepository = AuthRepository()
    private var data = MutableLiveData<ApiResult>()
    private var registerState = MutableLiveData<AuthState>()

    fun onLogin(user: User) {
        registerState.postValue(AuthState.Loading(isLoading = true))
        authRepository.onLogin(user).enqueue(object : Callback<ApiResult?> {
            override fun onResponse(call: Call<ApiResult?>, response: Response<ApiResult?>) {
                var result = response.body() as ApiResult
                if (result.success == true) {
                    registerState.postValue(AuthState.Loading(isLoading = false))
                    registerState.postValue(AuthState.Successful(success = true))
                    data.value = result
                } else {
                    registerState.postValue(AuthState.Loading(isLoading = false))
                    registerState.postValue(AuthState.Successful(success = false))
                }
            }
            override fun onFailure(call: Call<ApiResult?>, t: Throwable) {
                registerState.postValue(AuthState.Loading(isLoading = false))
                registerState.postValue(AuthState.Successful(success = false))
                registerState.postValue(AuthState.Failure(msg = t.message))
            }
        })
    }
}

