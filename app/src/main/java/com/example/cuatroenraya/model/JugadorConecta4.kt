package com.example.cuatroenraya.model

import java.io.File
import android.support.design.widget.Snackbar
import android.view.View
import com.example.cuatroenraya.R
import es.uam.eps.multij.*

/**
 * @brief Jugador conecta 4
 * @param nombre jugador
 */
class JugadorConecta4
constructor(private val nombre: String) : View.OnClickListener, Jugador {

	val OPCIONES_CORRECTAS = arrayListOf<Int>(0,1,2,3,4,5,6,8,9)

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

    private lateinit var game: Partida
    /**
     * @brief establece la partida
     * @param game partida
     */
    fun setPartida(game: Partida) {
        this.game = game
    }

    /**
     * @brief onclick register
     * @param v vista de partida
     */
    override fun onClick(v: View) {
        try {
            if (game.tablero.estado != Tablero.EN_CURSO) {
                Snackbar.make(v, "round_already_finished", Snackbar.LENGTH_SHORT).show()
                return
            }
            val m: Movimiento = MovimientoConecta4(fromViewToJ(v))
            val a = AccionMover(this, m)
            if (false != game.tablero.esValido(m)) {
                game.realizaAccion(a)
            } else {
                throw Exception("Columna llena")
            }
        } catch (e: Exception) {
            var errorString = e.message
            Snackbar.make(v, errorString.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    /**
     * @brief Devuelve el nombre del jugador
     */
    override fun getNombre() = nombre

    /**
     * @brief Este jugador juega *todos* los juegos
     */
    override fun puedeJugar(p0: Tablero?) = true

    /**
     * @brief oncambio partida
     * @param p0 evento
     */
    override fun onCambioEnPartida(p0: Evento) {}

    /**
     * @brief devuelve la columna
     * @param view vista
     */
    private fun fromViewToI(view: View): Int {
        for (i in 0 until ids.size)
            for (j in 0 until ids[i].size) {
                if (view.id == ids[i][j])
                    return i
            }
        return -1
    }

    /**
     * @brief devuelve la fila
     * @param view vista
     */
    private fun fromViewToJ(view: View): Int {
        for (i in 0 until ids.size)
            for (j in 0 until ids[i].size)
                if (view.id == ids[i][j])
                    return j
        return -1
    }

}
