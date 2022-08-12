package com.example.test_app.data.models

data class ApiResult(
    var message: String? = null,
    var success: Boolean? = false,
    var errorCode: String? = null,
    var data: Any? = null
) {
}