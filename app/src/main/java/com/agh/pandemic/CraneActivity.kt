package com.agh.pandemic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button

class CraneActivity : AppCompatActivity() {
    internal var threatDiscardPile: MutableList<ThreatCart> = ArrayList<ThreatCart>()
    internal var infectionCards: MutableList<ThreatCart> = ArrayList()

    internal lateinit var addInfectionTextView: AutoCompleteTextView
    internal lateinit var addInfectionButton: Button
    internal lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crane)
        addInfectionButton = findViewById(R.id.addInfectionButton)
        addInfectionTextView = findViewById(R.id.addInfectionTextView)
        startButton = findViewById(R.id.startWithCraneButton)

        threatDiscardPile = intent.getParcelableArrayListExtra<ThreatCart>("discard_pile")!!

        val cities = resources.getStringArray(R.array.pandemic_0_cities_array)
        val autoCompleteAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cities)
        addInfectionTextView.setAdapter(autoCompleteAdapter)
        addInfectionTextView.threshold = 1

        addInfectionButton.setOnClickListener { addInfection() }
        startButton.setOnClickListener {
            val intent = Intent(this, GameSeason0Activity::class.java)
            intent.putParcelableArrayListExtra("discard_pile", threatDiscardPile as java.util.ArrayList<out Parcelable>)
            intent.putParcelableArrayListExtra("infection_cards", infectionCards as java.util.ArrayList<out Parcelable>)
            startActivity(intent)
        }
    }

    private fun addInfection() {
        val threatCart = ThreatCart(addInfectionTextView.text.toString(), infection = true)
        threatDiscardPile.add(threatCart)
        infectionCards.add(threatCart)
        addInfectionTextView.setText("")
    }
}