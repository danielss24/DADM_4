package com.example.cuatroenraya.utility

import android.graphics.Color
import android.graphics.Paint
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import com.example.cuatroenraya.R
import com.example.cuatroenraya.activities.RoundAdapter
import com.example.cuatroenraya.model.TableroConecta4
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.model.RoundRepository

/**
 * @brief Actualiza las imagenes en funcion de su esado
 * @param board tablero
 * @param i columna del tablero
 * @param j fila del tablero
 */
fun ImageButton.update(board: TableroConecta4, i: Int, j: Int) {

    if (board.getTablero(i, j) == board.JUGADOR1)
        setBackgroundResource(R.drawable.circle__red_24dp)
    else if (board.getTablero(i, j) == board.IS_EMPTY)
        setBackgroundResource(R.drawable.circle__empty_24dp)
    else
        setBackgroundResource(R.drawable.circle__black_24dp)
}

/**
 * @brief Pinta los botones
 * @param board tablero
 * @param i columna del tablero
 * @param j fila del tablero
 */
fun Paint.setColor(board: TableroConecta4, i: Int, j: Int) {
    if (board.getTablero(i, j) === board.JUGADOR1)
        setColor(Color.parseColor("#00574B"))
    else if (board.getTablero(i, j) === board.IS_EMPTY)
        setColor(Color.GRAY)
    else
        setColor(Color.parseColor("#D81B60"))
}

/**
 * @brief addon para la vista recicladora
 * @param onClickListener clickable para la vista recicladora
 */
fun RecyclerView.update(onClickListener: (Round) -> Unit) {
    if (adapter == null){
        adapter = RoundAdapter(RoundRepository.rounds, onClickListener)
    }else{
        adapter!!.notifyDataSetChanged()
    }

}

/**
 * @brief addon para fragmentos
 * @param operations transaccion entre fragmentos
 */
fun FragmentManager.executeTransaction(operations: (FragmentTransaction.() -> Unit)) {
    val transaction = beginTransaction()
    transaction.operations()
    transaction.commit()
}