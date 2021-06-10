package com.agh.pandemic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    internal lateinit var season0Button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        season0Button = findViewById(R.id.season0Button)
        season0Button.setOnClickListener {
            val intent = Intent(this, Preparation0Activity::class.java)
            startActivity(intent)
        }
    }
}