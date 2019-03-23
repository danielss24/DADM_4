package com.example.cuatroenraya.utility

import android.widget.ImageButton
import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.TableroConecta4

fun ImageButton.update(board: TableroConecta4, i: Int, j: Int) {
    var tableroTMP = board.tablero

    if (board.getTablero(i, j) == board.JUGADOR1)
        setBackgroundResource(R.drawable.circle__red_24dp)
    else if (board.getTablero(i, j) == board.IS_EMPTY)
        setBackgroundResource(R.drawable.circle__empty_24dp)
    else
        setBackgroundResource(R.drawable.circle__black_24dp)
}