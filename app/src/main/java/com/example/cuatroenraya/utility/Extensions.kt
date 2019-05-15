package com.example.cuatroenraya.utility

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import com.example.cuatroenraya.R
import com.example.cuatroenraya.activities.RoundAdapter
import com.example.cuatroenraya.activities.SettingsActivity
import com.example.cuatroenraya.database.DataBase
import com.example.cuatroenraya.firebase.FBDataBase
import com.example.cuatroenraya.model.TableroConecta4
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.model.RoundRepository
import com.example.cuatroenraya.model.RoundRepositoryFactory
import java.lang.Exception


/**
 * @brief Pinta los botones
 * @param board tablero
 * @param i columna del tablero
 * @param j fila del tablero
 */
fun Paint.setColor(board: TableroConecta4, i: Int, j: Int, context: Context) {
    if (board.getTablero(i, j) === board.JUGADOR1) {
        var color = SettingsActivity.getColorP1(context)
        var colorInt = Color.parseColor(color)
        setColor(colorInt)
    } else if (board.getTablero(i, j) === board.IS_EMPTY)
        setColor(ContextCompat.getColor(context, R.color.colorNoPlayer))
    else {
        var color = SettingsActivity.getColorP2(context)
        var colorInt = Color.parseColor(color)
        setColor(colorInt)
    }
}

/**
 * @brief addon para la vista recicladora
 * @param onClickListener clickable para la vista recicladora
 */

fun RecyclerView.update(userName: String, onClickListener: (Round) -> Unit) {
    val repository = RoundRepositoryFactory.createRepository(context)
    val roundsCallback = object : RoundRepository.RoundsCallback {
        override fun onResponse(rounds: List<Round>) {
            if (adapter == null)
                adapter = RoundAdapter(rounds, onClickListener)
            else {
                (adapter as RoundAdapter).rounds = rounds
                adapter?.notifyDataSetChanged()
            }
        }

        override fun onError(error: String) {
            Snackbar.make(findViewById(R.id.recyclerView),error, Snackbar.LENGTH_LONG).show()
        }
    }

    if (repository is DataBase){
        repository?.getRounds(userName, "", "", roundsCallback)
    }else if (repository is FBDataBase){
        repository.startListeningChanges(roundsCallback)
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