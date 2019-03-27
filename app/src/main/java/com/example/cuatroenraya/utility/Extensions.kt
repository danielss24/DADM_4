package com.example.cuatroenraya.utility

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

fun ImageButton.update(board: TableroConecta4, i: Int, j: Int) {

    if (board.getTablero(i, j) == board.JUGADOR1)
        setBackgroundResource(R.drawable.circle__red_24dp)
    else if (board.getTablero(i, j) == board.IS_EMPTY)
        setBackgroundResource(R.drawable.circle__empty_24dp)
    else
        setBackgroundResource(R.drawable.circle__black_24dp)
}

fun RecyclerView.update(onClickListener: (Round) -> Unit) {
    if (adapter == null){
        adapter = RoundAdapter(RoundRepository.rounds, onClickListener)
    }else{
        adapter!!.notifyDataSetChanged()
    }
}
fun FragmentManager.executeTransaction(operations: (FragmentTransaction.() -> Unit)) {
    val transaction = beginTransaction()
    transaction.operations()
    transaction.commit()
}


fun View.setPlayerAsOnClickListener(jugador: View.OnClickListener) {
    val ids = arrayOf(
        intArrayOf(
            R.id.c11,
            R.id.c12,
            R.id.c13,
            R.id.c14,
            R.id.c15,
            R.id.c16,
            R.id.c17
        ),
        intArrayOf(
            R.id.c21,
            R.id.c22,
            R.id.c23,
            R.id.c24,
            R.id.c25,
            R.id.c26,
            R.id.c27
        ),
        intArrayOf(
            R.id.c31,
            R.id.c32,
            R.id.c33,
            R.id.c34,
            R.id.c35,
            R.id.c36,
            R.id.c37
        ),
        intArrayOf(
            R.id.c41,
            R.id.c42,
            R.id.c43,
            R.id.c44,
            R.id.c45,
            R.id.c46,
            R.id.c47
        ),
        intArrayOf(
            R.id.c51,
            R.id.c52,
            R.id.c53,
            R.id.c54,
            R.id.c55,
            R.id.c56,
            R.id.c57
        ),
        intArrayOf(
            R.id.c61,
            R.id.c62,
            R.id.c63,
            R.id.c64,
            R.id.c65,
            R.id.c66,
            R.id.c67
        )
    )
    var button: ImageButton
    for (i in 0 until ids.size)
        for (j in 0 until ids[i].size) {
            button = findViewById(ids[i][j])
            button.setOnClickListener(jugador)
        }
}

fun View.update(round: Round) {

    val ids = arrayOf(
        intArrayOf(
            R.id.c11,
            R.id.c12,
            R.id.c13,
            R.id.c14,
            R.id.c15,
            R.id.c16,
            R.id.c17
        ),
        intArrayOf(
            R.id.c21,
            R.id.c22,
            R.id.c23,
            R.id.c24,
            R.id.c25,
            R.id.c26,
            R.id.c27
        ),
        intArrayOf(
            R.id.c31,
            R.id.c32,
            R.id.c33,
            R.id.c34,
            R.id.c35,
            R.id.c36,
            R.id.c37
        ),
        intArrayOf(
            R.id.c41,
            R.id.c42,
            R.id.c43,
            R.id.c44,
            R.id.c45,
            R.id.c46,
            R.id.c47
        ),
        intArrayOf(
            R.id.c51,
            R.id.c52,
            R.id.c53,
            R.id.c54,
            R.id.c55,
            R.id.c56,
            R.id.c57
        ),
        intArrayOf(
            R.id.c61,
            R.id.c62,
            R.id.c63,
            R.id.c64,
            R.id.c65,
            R.id.c66,
            R.id.c67
        )
    )

    for (i in 0 until ids.size)
        for (j in 0 until ids[i].size) {
            val button = findViewById(ids[i][j]) as ImageButton
            with(button) {
                if (round.board.getTablero(i, j) == round.board.JUGADOR1)
                    setBackgroundResource(R.drawable.circle__red_24dp)
                else if (round.board.getTablero(i, j) == round.board.IS_EMPTY)
                    setBackgroundResource(R.drawable.circle__empty_24dp)
                else
                    setBackgroundResource(R.drawable.circle__black_24dp)
            }
        }
}
