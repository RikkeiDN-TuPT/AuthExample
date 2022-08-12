package com.example.test_app.data.models

sealed class AuthState{
    class Successful(val success: Boolean) : AuthState()
    class Failure(val msg: String?) : AuthState()
    class Loading(val isLoading: Boolean) : AuthState()
}
