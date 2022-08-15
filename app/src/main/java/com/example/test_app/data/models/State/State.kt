package com.example.test_app.data.models.State

sealed class State {
    class Successful(val success: Boolean = false) : State()
    class Failure(val msg: String = "") : State()
    class Loading(val isLoading: Boolean = false) : State()
}
