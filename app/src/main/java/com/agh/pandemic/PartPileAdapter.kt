package com.agh.pandemic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PartPileAdapter(var pile: List<ThreatCart>):
    RecyclerView.Adapter<PartPileAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val threatTextView: TextView = view.findViewById(R.id.threatTextView)
        val addThreatButton: Button = view.findViewById(R.id.addSingleThreatButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_threat_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val threat = pile.elementAt(position)
        holder.threatTextView.text = threat.toString()
        holder.addThreatButton.text = threat.toString()
        holder.addThreatButton.setOnClickListener { addThreat(threat) }
    }

    private fun addThreat(threat: ThreatCart) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return pile.size
    }
}