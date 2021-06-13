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
    internal lateinit var escalationButton: Button
    internal lateinit var intensificationButton: Button
    internal lateinit var counterespionageButton: Button
    internal lateinit var forecastButton: Button
    internal lateinit var recycler: RecyclerView
    internal lateinit var adapter: WholePileAdapter
    internal var threatDiscardPile: MutableList<ThreatCart> = ArrayList()
    internal var threatPile: MutableList<ThreatCart> = ArrayList()
    internal lateinit var piles: MutableList<MutableList<ThreatCart>>
    internal lateinit var allThreats: MutableList<String>
    internal lateinit var autoCompleteAdapter: ArrayAdapter<String>
    internal var counterespionageUsed: Boolean = false
    internal var forecastUsed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_season0)

        addThreatTextView = findViewById(R.id.addThreatTextView)
        allThreats = resources.getStringArray(R.array.pandemic_0_cities_array).toMutableList()
        val infectionCards = intent.getParcelableArrayListExtra<ThreatCart>("infection_cards")
        if (infectionCards != null) {
            allThreats.addAll(infectionCards.map { it.toString() })
        }
        autoCompleteAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, allThreats)
        addThreatTextView.setAdapter(autoCompleteAdapter)
        addThreatTextView.threshold = 1

        threatDiscardPile = intent.getParcelableArrayListExtra<ThreatCart>("discard_pile")!!

        threatPile = resources.getStringArray(R.array.pandemic_0_cities_array).map { ThreatCart(it, infection = false) }.toMutableList()
        threatDiscardPile.forEach {
            if (!it.infection) {
                threatPile.remove(it)
            }
        }

        addThreatButton = findViewById(R.id.addThreatButton)
        addThreatButton.setOnClickListener { addThreat() }

        escalationButton = findViewById(R.id.escalationButton)
        escalationButton.setOnClickListener { startEscalation() }

        intensificationButton = findViewById(R.id.intensificationButton)
        intensificationButton.setOnClickListener { intensification() }
        intensificationButton.isEnabled = false

        counterespionageButton = findViewById(R.id.counterespionageButton)
        counterespionageButton.setOnClickListener { counterespionage() }

        forecastButton = findViewById(R.id.forecastButton)
        forecastButton.setOnClickListener { forecast() }

        recycler = findViewById(R.id.ThreatPileRecyclerView)
        recycler.layoutManager = LinearLayoutManager(this)
        piles = mutableListOf(threatPile)
        adapter = WholePileAdapter(piles)
        recycler.adapter = adapter
    }

    private fun forecast() {
        addThreatTextView.hint = "Wprowadzaj karty od ostatniej"
        addThreatButton.setText("Prognozuj (6)")
        addThreatButton.setOnClickListener { forecastButtonAction(6) }

        escalationButton.isEnabled = false
        counterespionageButton.isEnabled = false
        forecastButton.isEnabled = false
    }

    private fun forecastButtonAction(cardsLeft: Int) {
        val threatName = addThreatTextView.text.toString()
        val regex = "Infekcja".toRegex()
        val match = regex.find(threatName)
        var threatCart = ThreatCart(threatName, infection = false)
        if (match != null) {
            val cityName = threatName.substring(9)
            threatCart = ThreatCart(cityName, infection = true)
        }

        var emptyPileIndex = -1
        for ((index, pile) in piles.withIndex()){
            pile.remove(threatCart)
            if (pile.size == 0){
                emptyPileIndex = index
            }
        }
        if (emptyPileIndex > -1){
            piles.removeAt(emptyPileIndex)
        }

        piles.add(0, mutableListOf(threatCart))
        recycler.adapter!!.notifyDataSetChanged()

        addThreatTextView.setText("")

        if (cardsLeft > 1) {
            addThreatButton.setText("Prognozuj (" + (cardsLeft - 1) + ")")
            addThreatButton.setOnClickListener { forecastButtonAction(cardsLeft - 1) }
        } else {
            addThreatButton.setText(R.string.addThreat)
            addThreatButton.setOnClickListener { addThreat() }
            addThreatTextView.hint = ""

            escalationButton.isEnabled = true
            counterespionageButton.isEnabled = !counterespionageUsed
            forecastButton.isEnabled = false
            forecastUsed = true
        }
    }

    private fun counterespionage() {
        addThreatTextView.hint = "Wprowadź usuwaną kartę"
        val discardedAutoCompleteAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, threatDiscardPile.map { it.toString() })
        addThreatTextView.setAdapter(discardedAutoCompleteAdapter)
        addThreatButton.setText(R.string.deleteCard)
        val currentEnableStatus = addThreatButton.isEnabled
        addThreatButton.setOnClickListener { removeCard(currentEnableStatus) }
        addThreatButton.isEnabled = true
    }

    private fun removeCard(state: Boolean) {
        val threatName = addThreatTextView.text.toString()
        val regex = "Infekcja".toRegex()
        val match = regex.find(threatName)
        var threatCart = ThreatCart(threatName, infection = false)
        if (match != null) {
            val cityName = threatName.substring(9)
            threatCart = ThreatCart(cityName, infection = true)
        }
        threatDiscardPile.remove(threatCart)
        addThreatTextView.hint = ""
        addThreatButton.setText(R.string.addThreat)
        addThreatButton.setOnClickListener { addThreat() }
        addThreatTextView.setText("")
        counterespionageButton.isEnabled = false
        counterespionageUsed = true
        addThreatButton.isEnabled = state
        addThreatTextView.setAdapter(autoCompleteAdapter)
    }

    private fun intensification() {
        piles.add(0, threatDiscardPile)
        recycler.adapter!!.notifyDataSetChanged()
        threatDiscardPile = emptyArray<ThreatCart>().toMutableList()
        addThreatButton.isEnabled = true
        intensificationButton.isEnabled = false
        escalationButton.isEnabled = true
        forecastButton.isEnabled = !forecastUsed
    }

    private fun startEscalation() {
        addThreatTextView.hint = "Wpisz kartę ze spodu"
        addThreatButton.setText(R.string.addAgents)
        addThreatButton.setOnClickListener { addAgents() }
        escalationButton.isEnabled = false
        forecastButton.isEnabled = false
        counterespionageButton.isEnabled = false
    }

    private fun addAgents() {
        val threatCart = ThreatCart(addThreatTextView.text.toString(), infection = false)
        threatDiscardPile.add(threatCart)
        piles.last().remove(threatCart)

        addThreatTextView.hint = ""
        addThreatButton.setText(R.string.addThreat)
        addThreatButton.setOnClickListener { addThreat() }
        addThreatButton.isEnabled = false
        addThreatTextView.setText("")
        recycler.adapter!!.notifyDataSetChanged()
        intensificationButton.isEnabled = true
        counterespionageButton.isEnabled = !counterespionageUsed
    }

    private fun addThreat() {
        val threatName = addThreatTextView.text.toString()
        val regex = "Infekcja".toRegex()
        val match = regex.find(threatName)
        var threatCart = ThreatCart(threatName, infection = false)
        if (match != null) {
            val cityName = threatName.substring(9)
            threatCart = ThreatCart(cityName, infection = true)
        }
        threatDiscardPile.add(threatCart)
        piles[0].remove(threatCart)
        if (piles[0].size == 0){
            piles.removeAt(0)
        }
        addThreatTextView.setText("")
        recycler.adapter!!.notifyDataSetChanged()
    }
}