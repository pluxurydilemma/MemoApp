package com.androiddev.memoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androiddev.memoapp.Fragments.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, MainFragment())
            commit()
        }
    }
}