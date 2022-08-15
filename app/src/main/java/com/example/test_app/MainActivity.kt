package com.example.test_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test_app.ui.home.HomeFragment
import com.example.test_app.ui.main.MainFragment
import com.example.test_app.utils.MyShareReferences

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var token = MyShareReferences(applicationContext).getToken()
        if (savedInstanceState == null) {
            if (token.isNullOrEmpty()) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance())
                    .commitNow()
            }

        }
    }
}