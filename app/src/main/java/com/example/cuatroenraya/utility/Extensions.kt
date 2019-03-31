package com.example.cuatroenraya.utility

import android.content.Context
import android.graphics.Paint
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import com.example.cuatroenraya.R
import com.example.cuatroenraya.activities.RoundAdapter
import com.example.cuatroenraya.model.TableroConecta4
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.model.RoundRepository


/**
 * @brief Pinta los botones
 * @param board tablero
 * @param i columna del tablero
 * @param j fila del tablero
 */
fun Paint.setColor(board: TableroConecta4, i: Int, j: Int, context: Context) {
    if (board.getTablero(i, j) === board.JUGADOR1)
        setColor(ContextCompat.getColor(context, R.color.colorPlayerOneChip))
    else if (board.getTablero(i, j) === board.IS_EMPTY)
        setColor(ContextCompat.getColor(context, R.color.colorNoPlayer))
    else
        setColor(ContextCompat.getColor(context, R.color.colorPlayerTwoChip))
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