package com.agh.pandemic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button

class Preparation0Activity : AppCompatActivity() {
    internal lateinit var addCityTextView: AutoCompleteTextView
    internal lateinit var addThreatButton: Button
    internal lateinit var craneButton: Button
    internal lateinit var startButton: Button
    internal lateinit var pile: MutableList<ThreatCart>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preparation0)
        pile = ArrayList<ThreatCart>()

        addCityTextView = findViewById(R.id.addInitialThreatTextView)
        addThreatButton = findViewById(R.id.addInitialThreatButton)
        craneButton = findViewById(R.id.craneButton)
        startButton = findViewById(R.id.startButton)

        val cities = resources.getStringArray(R.array.pandemic_0_cities_array)
        val autoCompleteAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cities)
        addCityTextView.setAdapter(autoCompleteAdapter)
        addCityTextView.threshold = 1

        addThreatButton.setOnClickListener { addThreat() }

        craneButton.setOnClickListener {
            val intent = Intent(this, CraneActivity::class.java)
            intent.putParcelableArrayListExtra("discard_pile", pile as java.util.ArrayList<out Parcelable>)
            startActivity(intent)
        }

        startButton.setOnClickListener {
            val intent = Intent(this, GameSeason0Activity::class.java)
            intent.putParcelableArrayListExtra("discard_pile", pile as java.util.ArrayList<out Parcelable>)
            startActivity(intent)
        }
    }

    private fun addThreat() {
        pile.add(ThreatCart(addCityTextView.text.toString(), infection=false))
        addCityTextView.setText("")
    }
}