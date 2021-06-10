package com.agh.pandemic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Button

class CraneActivity : AppCompatActivity() {
    internal var threatDiscardPile: List<ThreatCart>? = ArrayList<ThreatCart>()

    internal lateinit var addInfectionTextView: AutoCompleteTextView
    internal lateinit var addInfectionButton: Button
    internal lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crane)
        addInfectionButton = findViewById(R.id.addInfectionButton)
        addInfectionTextView = findViewById(R.id.addInfectionTextView)
        startButton = findViewById(R.id.startWithCraneButton)

        threatDiscardPile = intent.getParcelableArrayListExtra<ThreatCart>("discard_pile")
    }
}