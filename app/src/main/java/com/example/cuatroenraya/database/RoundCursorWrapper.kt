package com.example.cuatroenraya.database

import android.database.Cursor
import android.database.CursorWrapper
import android.util.Log
import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.Round
import es.uam.eps.multij.ExcepcionJuego
import com.example.cuatroenraya.database.RoundDataBaseSchema.RoundTable
import com.example.cuatroenraya.database.RoundDataBaseSchema.UserTable
import android.content.res.Resources

class RoundCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {
    private val DEBUG = "DEBUG"
    val round: Round
        get() {
            val playername = getString(getColumnIndex(UserTable.Cols.PLAYERNAME))
            val playeruuid = getString(getColumnIndex(UserTable.Cols.PLAYERUUID))
            val rounduuid = getString(getColumnIndex(RoundTable.Cols.ROUNDUUID))
            val date = getString(getColumnIndex(RoundTable.Cols.DATE))
            val title = getString(getColumnIndex(RoundTable.Cols.TITLE))
            val board = getString(getColumnIndex(RoundTable.Cols.BOARD))
            val round = Round()
            round.firstPlayerName = playername
            round.firstPlayerUUID = playeruuid
            round.secondPlayerName = "jugador_OPEN"
            round.secondPlayerUUID = "jugador_OPEN"
            round.id = rounduuid
            round.date = date
            round.title = title
            try {
                round.board.stringToTablero(board)
            } catch (e: ExcepcionJuego) {
                Log.d(DEBUG, "Error turning string into tablero")
            }
            return round
        }
}