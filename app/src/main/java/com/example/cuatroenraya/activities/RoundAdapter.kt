package com.example.cuatroenraya.activities

import android.app.Application
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.R
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.cuatroenraya.views.ERView
import es.uam.eps.multij.Tablero

/**
 * @brief clase contenedora de partidas guardadas
 * @param itemView vista de partidas guardadas
 */
class RoundHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener {
    var idTextView: TextView
    var board_erview: ERView
    var dateTextView: TextView
    var layoutId: RelativeLayout
    var plater1Text: TextView
    var plater2Text: TextView
    var turnoText: TextView
    init {
        idTextView = itemView.findViewById(R.id.list_item_id) as TextView
        board_erview = itemView.findViewById(R.id.board_erview_list) as ERView
        dateTextView = itemView.findViewById(R.id.list_item_date) as TextView
        layoutId = itemView.findViewById(R.id.list_item_layout) as RelativeLayout
        plater1Text = itemView.findViewById(R.id.list_item_player1) as TextView
        plater2Text = itemView.findViewById(R.id.list_item_player2) as TextView
        turnoText = itemView.findViewById(R.id.list_item_turno) as TextView

        itemView.setOnClickListener(this)
    }

    /**
     * @brief asigna valores de partida guardada
     * @param round partida a guardar
     * @param listener listener de partida
     */
    fun bindRound(round: Round, listener: (Round) -> Unit) {
        idTextView.text = round.title
        board_erview.setBoard(round.board)
        dateTextView.text = round.date.toString().substring(0,19)

        plater1Text.text = itemView.context.resources.getString(R.string.player1) + ": " + round.firstPlayerName
        plater2Text.text = itemView.context.resources.getString(R.string.player2) + ": " + round.secondPlayerName
        turnoText.text = ganaOMueve(round)



        idTextView.setOnClickListener { listener(round) }
        dateTextView.setOnClickListener { listener(round) }
        layoutId.setOnClickListener { listener(round) }
        plater1Text.setOnClickListener { listener(round) }
        plater2Text.setOnClickListener { listener(round) }

    }

    /**
     * @brief Manda el nombre del jugador que le toque mover
     * @param round partida a guardar
     * @return el nombre del jugador
     */
    private fun textoMueveFun(round: Round): String{
        if (round.board.turno == 0){
            return itemView.context.resources.getString(R.string.mueve_string) + ": " + round.firstPlayerName
        }else{
            return itemView.context.resources.getString(R.string.mueve_string) + ": " + round.secondPlayerName
        }
    }

    /**
     * @brief Determina si poner el jughador que le toca mover o partida finalizada
     * @param round partida a guardar
     * @return la cadena correcta
     */
    private fun ganaOMueve(round: Round): String{
        if (round.board.estado == Tablero.FINALIZADA){
            return ganaJugador(round)
        }else{
            return textoMueveFun(round)
        }
    }

    /**
     * @brief Devuelve el jugador que ha ganado
     * @param round partida a guardar
     * @return el nombre del jugador
     */

    private fun ganaJugador(round: Round): String{
        if (round.board.turno == 0){
            return itemView.context.resources.getString(R.string.gana_string) + ": " + round.secondPlayerName
        }else{
            return itemView.context.resources.getString(R.string.gana_string) + ": " + round.firstPlayerName
        }
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
class RoundAdapter(var rounds: List<Round>, var listener: (Round) -> Unit) : RecyclerView.Adapter<RoundHolder>() {
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

