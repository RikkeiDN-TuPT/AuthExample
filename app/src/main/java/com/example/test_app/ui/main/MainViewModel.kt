package com.example.test_app.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test_app.data.models.ApiResult
import com.example.test_app.data.models.User
import com.example.test_app.data.repository.AuthRepository

class MainViewModel : ViewModel() {
    private var authRepository: AuthRepository = AuthRepository()
    private var data: MutableLiveData<ApiResult>? = null


    fun onLogin(user: User): MutableLiveData<ApiResult> {
        this.data = authRepository.onLogin(user)
        return data as MutableLiveData<ApiResult>
    };

}