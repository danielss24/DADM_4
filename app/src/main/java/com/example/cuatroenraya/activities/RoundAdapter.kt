package com.example.cuatroenraya.activities

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.R
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout

/**
 * @brief clase contenedora de partidas guardadas
 * @param itemView vista de partidas guardadas
 */
class RoundHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener {
    var idTextView: TextView
    var boardTextView: TextView
    var dateTextView: TextView
    var layoutId: RelativeLayout
    init {
        idTextView = itemView.findViewById(R.id.list_item_id) as TextView
        boardTextView = itemView.findViewById(R.id.list_item_board) as TextView
        dateTextView = itemView.findViewById(R.id.list_item_date) as TextView
        layoutId = itemView.findViewById(R.id.list_item_layout) as RelativeLayout
        itemView.setOnClickListener(this)
    }

    /**
     * @brief asigna valores de partida guardada
     * @param round partida a guardar
     * @param listener listener de partida
     */
    fun bindRound(round: Round, listener: (Round) -> Unit) {
        idTextView.text = round.title
        boardTextView.text = round.board?.imprimeTablero()
        dateTextView.text = round.date.toString().substring(0,19)
        idTextView.setOnClickListener { listener(round) }
        boardTextView.setOnClickListener { listener(round) }
        dateTextView.setOnClickListener { listener(round) }
        layoutId.setOnClickListener { listener(round) }

    }

    /**
     * @brief onclick de vista
     * @param v vista
     */
    override fun onClick(v: View?) {
        Snackbar.make(itemView, "Item ${idTextView.text} selected",Snackbar.LENGTH_SHORT).show()
    }
}

/**
 * @brief clase adaptadora de partidas guardadas
 * @param rounds partidas guardadas
 * @param listener listener de partidas guardadas
 */
class RoundAdapter(val rounds: List<Round>, val listener: (Round) -> Unit): RecyclerView.Adapter<RoundHolder>() {
    /**
     * @brief creadora de vista/controlador
     * @param parent conjunto de vistas
     * @param viewType vista
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_round, parent, false)
        return RoundHolder(view)
    }

    /**
     * @brief cuenta las partidas guardadas
     */
    override fun getItemCount(): Int = rounds.size

    /**
     * @brief colocacion de partidas guardadas
     * @param holder manejador
     */
    override fun onBindViewHolder(holder: RoundHolder, position: Int) {
        holder.bindRound(rounds[position], listener)
    }

}

