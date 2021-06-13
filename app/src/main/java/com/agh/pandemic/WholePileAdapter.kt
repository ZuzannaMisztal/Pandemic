package com.agh.pandemic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WholePileAdapter(var pile: List<List<ThreatCart>>): RecyclerView.Adapter<WholePileAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val recycler: RecyclerView = view.findViewById(R.id.PilesRecyclerView)
    }

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.part_of_pile, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val adapter = PartPileAdapter(pile.elementAt(position))
        holder.recycler.layoutManager = LinearLayoutManager(holder.recycler.context, RecyclerView.VERTICAL, false)
        holder.recycler.adapter = adapter
        holder.recycler.setRecycledViewPool(viewPool)

    }

    override fun getItemCount(): Int {
        return pile.size
    }
}