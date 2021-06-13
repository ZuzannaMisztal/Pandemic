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
        val threatTextView: TextView = view.findViewById(R.id.singleThreatTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_threat_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val threat = pile.elementAt(position)
        holder.threatTextView.text = threat.toString()
    }

    override fun getItemCount(): Int {
        return pile.size
    }
}