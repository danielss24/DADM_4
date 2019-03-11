package com.example.cuatroenraya.activities

import android.widget.ImageButton
import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.Board
import org.json.JSONObject
fun ImageButton.update(board: ERBoard, i: Int, j: Int) {
    if (board.getTablero(i, j) == ERBoard.JUGADOR1)
        setBackgroundResource(R.drawable.blue_button_48dp)
    else if (board.getTablero(i, j) == ERBoard.VACIO)
        setBackgroundResource(R.drawable.void_button_48dp)
    else
        setBackgroundResource(R.drawable.green_button_48dp)
}