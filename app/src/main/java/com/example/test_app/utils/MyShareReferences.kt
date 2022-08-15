package com.example.test_app.utils

import android.content.Context
import android.content.SharedPreferences

class MyShareReferences(context: Context) {
    var myContext: Context
    private lateinit var ref: SharedPreferences
    private val myToken = "TOKEN"

    init {
        myContext = context
        ref = myContext.getSharedPreferences("REF", Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        ref.edit().putString(myToken, token).commit()
    }

    fun getToken(): String {
        return ref.getString(myToken, "").toString()
    }
}

