package com.example.cuatroenraya
import android.support.design.widget.Snackbar
import android.view.View
import es.uam.eps.multij.*
class LocalPlayer: View.OnClickListener, Jugador {
    val ids = arrayOf(intArrayOf(
        R.id.c11,
        R.id.c12,
        R.id.c13,
        R.id.c14,
        R.id.c15,
        R.id.c16
    ),
        intArrayOf(
            R.id.c21,
            R.id.c22,
            R.id.c23,
            R.id.c24,
            R.id.c25,
            R.id.c26
        ),
        intArrayOf(
            R.id.c31,
            R.id.c32,
            R.id.c33,
            R.id.c34,
            R.id.c35,
            R.id.c36
        ),
        intArrayOf(
            R.id.c41,
            R.id.c42,
            R.id.c43,
            R.id.c44,
            R.id.c45,
            R.id.c46
        ),
        intArrayOf(
            R.id.c51,
            R.id.c52,
            R.id.c53,
            R.id.c54,
            R.id.c55,
            R.id.c56
        ),
        intArrayOf(
            R.id.c61,
            R.id.c62,
            R.id.c63,
            R.id.c64,
            R.id.c65,
            R.id.c66
        )
    )
    private lateinit var game: Partida

    fun setPartida(game: Partida) {
        this.game = game
    }
    override fun onClick(v: View) {
        try {
            if (game.tablero.estado != Tablero.EN_CURSO) {
                //TODO
                // Comprobar la cadena
                //Snackbar.make(v, R.string.round_already_finished,Snackbar.LENGTH_SHORT).show()
                Snackbar.make(v, "round_already_finished",Snackbar.LENGTH_SHORT).show()
                return
            }
            //TODO
            val m: Movimiento = MovimientoConecta4(fromViewToJ(v))
            val a = AccionMover(this, m)
            game.realizaAccion(a)
        } catch (e: Exception) {
            //TODO
            // Comprobar la cadena
            //Snackbar.make(v, R.string.invalid_movement,Snackbar.LENGTH_SHORT).show()
            Snackbar.make(v, "invalid_movement",Snackbar.LENGTH_SHORT).show()
        }
    }
    override fun getNombre() = "ER local player"
    override fun puedeJugar(p0: Tablero?) = true
    override fun onCambioEnPartida(p0: Evento) {}
    private fun fromViewToI(view: View): Int {
        for (i in 0 until ids.size)
            for (j in 0 until ids.size) {
                if (view.id == ids[i][j])
                    return i
            }
        return -1
    }
    private fun fromViewToJ(view: View): Int {
        for (i in 0 until ids.size)
            for (j in 0 until ids.size)
                if (view.id == ids[i][j])
                    return j
        return -1
    }
}