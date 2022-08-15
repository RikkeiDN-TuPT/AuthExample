package com.example.test_app.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test_app.data.models.ApiResult
import com.example.test_app.data.models.State.State
import com.example.test_app.data.models.User
import com.example.test_app.data.repository.AuthRepository
import com.example.test_app.utils.MyShareReferences
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainViewModel : ViewModel() {
    private var authRepository: AuthRepository = AuthRepository()
    private var data = MutableLiveData<ApiResult>()
    private var registerState = MutableLiveData<State>()

    //    private lateinit var mContext: Context
    private lateinit var mySharedPreferences: MyShareReferences

    fun initContext(context: Context) {
        mySharedPreferences = MyShareReferences(context)
    }

    fun onLogin(user: User) {
        registerState.postValue(State.Loading(isLoading = true))
        authRepository.onLogin(user).enqueue(object : Callback<ApiResult> {
            override fun onResponse(call: Call<ApiResult?>, response: Response<ApiResult>) {
                var result = response.body() as ApiResult
                try {
                    if (result.success == true) {
                        registerState.postValue(State.Loading(isLoading = false))
                        data.value = result
                        var yourMap: LinkedTreeMap<String, Objects> =
                            result.data as LinkedTreeMap<String, Objects>
                        val gson = Gson()
                        val jsonObject = gson.toJsonTree(yourMap).asJsonObject
                        val user: User = gson.fromJson(jsonObject, User::class.java)
                        val token: String = user.token
                        mySharedPreferences.saveToken(token)
                        registerState.postValue(State.Successful(success = true))

                    } else {
                        registerState.postValue(State.Loading(isLoading = false))
                        registerState.postValue(State.Successful(success = false))
                    }
                } catch (e: Exception) {
                    registerState.postValue(State.Loading(isLoading = false))
                    registerState.postValue(State.Successful(success = false))
                    registerState.postValue(e.message?.let { State.Failure(msg = it) })
                }

            }

            override fun onFailure(call: Call<ApiResult?>, t: Throwable) {
                registerState.postValue(State.Loading(isLoading = false))
                registerState.postValue(State.Successful(success = false))
                registerState.postValue(t.message?.let { State.Failure(msg = it) })
            }
        })
    }

    fun getAuthState(): MutableLiveData<State> {
        return registerState
    }
}

