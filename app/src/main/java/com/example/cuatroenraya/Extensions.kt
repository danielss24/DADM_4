package com.example.cuatroenraya

import android.widget.ImageButton
import com.example.cuatroenraya.R
import org.json.JSONObject

//TODO
//Verificar los iconos y el id de JUGADOR1
fun ImageButton.update(board: TableroConecta4, i: Int, j: Int) {
    if (board.getTablero(i, j) == board.JUGADOR1)
        setBackgroundResource(R.drawable.casilla_vacia_24dpfilled)
    else if (board.getTablero(i, j) == board.IS_EMPTY)
        setBackgroundResource(R.drawable.casilla_vacia_24dp)
    else
        setBackgroundResource(R.drawable.casilla_vacia_24dpfilled)
}