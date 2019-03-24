package com.example.cuatroenraya.activities

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.example.cuatroenraya.model.Round

class RoundHolder(val textView: TextView): RecyclerView.ViewHolder(textView) {}

class RoundAdapter(val rounds: List<Round>): RecyclerView.Adapter<RoundHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundHolder {
        return RoundHolder(TextView(parent.context))
    }
    override fun getItemCount(): Int = rounds.size

    override fun onBindViewHolder(holder: RoundHolder, position: Int) {
        holder.textView.text = "${rounds[position].title} \n ${rounds[position].date.substringBefore("GMT")} \n"
    }
}

