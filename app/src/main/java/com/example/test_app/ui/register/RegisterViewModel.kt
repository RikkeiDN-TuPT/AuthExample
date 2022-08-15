package com.example.test_app.ui.register

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test_app.data.models.ApiResult
import com.example.test_app.data.models.State.State
import com.example.test_app.data.models.User
import com.example.test_app.data.repository.AuthRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    private var registerState = MutableLiveData<State>()
    private val authRepository = AuthRepository()
    lateinit var viewModelContext: Context

    fun initContext(context: Context) {
        viewModelContext = context
    }

    fun getRegisterState(): MutableLiveData<State> {
        return registerState
    }

    fun register(user: User) {
        registerState.postValue(State.Loading(isLoading = true))
        authRepository.onRegister(user).enqueue(object : Callback<ApiResult> {
            override fun onResponse(call: Call<ApiResult>, response: Response<ApiResult>) {
                registerState.postValue(State.Loading(isLoading = false))
                try {
                    if (response != null) {
                        val result = response.body() as ApiResult
                        if (result.success == true) {
                            registerState.postValue(
                                State.Successful(success = true)
                            )
                        } else {
                            registerState.postValue(
                                State.Successful(success = false)
                            )
                            registerState.postValue(State.Failure(msg = result.message.toString()))
                        }
                    }
                } catch (e: Exception) {
                    registerState.postValue(State.Failure(msg = e.message.toString()))
                    registerState.postValue(State.Successful(success = false))
                }

            }

            override fun onFailure(call: Call<ApiResult>, t: Throwable) {
                registerState.postValue(State.Loading(isLoading = false))
                registerState.postValue(State.Failure(msg = t.toString()))
                registerState.postValue(State.Successful(success = false))
            }
        })
    }
}

