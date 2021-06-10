package com.agh.pandemic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GameSeason0Activity : AppCompatActivity() {
    internal lateinit var addThreatTextView: AutoCompleteTextView
    internal lateinit var addThreatButton: Button
    internal lateinit var recycler: RecyclerView
    internal lateinit var adapter: WholePileAdapter
    internal var threatDiscardPile: List<ThreatCart> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_season0)

        addThreatTextView = findViewById(R.id.addThreatTextView)
        var cities = resources.getStringArray(R.array.pandemic_0_cities_array).toMutableList()
        val autoCompleteAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cities)
        addThreatTextView.setAdapter(autoCompleteAdapter)
        addThreatTextView.threshold = 1

        threatDiscardPile = intent.getParcelableArrayListExtra<ThreatCart>("discard_pile")!!
        println("Intent " + threatDiscardPile)

        addThreatButton = findViewById(R.id.addThreatButton)
        recycler = findViewById(R.id.ThreatPileRecyclerView)
        recycler.layoutManager = LinearLayoutManager(this)
        val pile = listOf(threatDiscardPile, threatDiscardPile) // powinny być nieodrzucone
        adapter = WholePileAdapter(pile)
        recycler.adapter = adapter
    }
}