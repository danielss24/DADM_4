package com.example.cuatroenraya.activities

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.R
import android.support.design.widget.Snackbar
import android.view.LayoutInflater


class RoundHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener {
    lateinit var idTextView: TextView
    lateinit var boardTextView: TextView
    lateinit var dateTextView: TextView
    init {
        idTextView = itemView.findViewById(R.id.list_item_id) as TextView
        boardTextView = itemView.findViewById(R.id.list_item_board) as TextView
        dateTextView = itemView.findViewById(R.id.list_item_date) as TextView
        itemView.setOnClickListener(this)
    }
    fun bindRound(round: Round, listener: (Round) -> Unit) {
        idTextView.text = round.title
        boardTextView.text = round.board?.imprimeTablero()
        dateTextView.text = round.date.toString().substring(0,19)
        itemView.setOnClickListener { listener(round) }
    }
    override fun onClick(v: View?) {
        Snackbar.make(itemView, "Item ${idTextView.text} selected",Snackbar.LENGTH_SHORT).show()
    }
}

class RoundAdapter(val rounds: List<Round>, val listener: (Round) -> Unit): RecyclerView.Adapter<RoundHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_round, parent, false)
        return RoundHolder(view)
    }
    override fun getItemCount(): Int = rounds.size
    override fun onBindViewHolder(holder: RoundHolder, position: Int) {
        holder.bindRound(rounds[position], listener)
    }

}

