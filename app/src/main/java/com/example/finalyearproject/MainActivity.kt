package com.example.finalyearproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object {
        private const val CLIENT_ID = "6573a6cbc21f424fad81067b6ce53fd0"
        private const val REDIRECT_URI = "http://com.example.finalyearproject/callback"
    }
}