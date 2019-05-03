package com.example.cuatroenraya.model

import android.preference.PreferenceManager
import java.io.File
import android.support.design.widget.Snackbar
import android.view.View
import com.example.cuatroenraya.R
import com.example.cuatroenraya.activities.SettingsActivity
import com.example.cuatroenraya.views.ERView
import es.uam.eps.multij.*

/**
 * @brief Jugador conecta 4
 * @param nombre jugador
 */
class JugadorConecta4
constructor(private val nombre: String) : ERView.OnPlayListener, Jugador {


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
     * @param column columna donmde poner la ficha
     */
    override fun onPlay(column: Int) {
        if (game.tablero.estado != Tablero.EN_CURSO) {
            return
        }
        val m: MovimientoConecta4
        m = MovimientoConecta4(column)
        if (game.tablero.esValido(m)==false){
            Snackbar.make(this as View,"Columna llena", Snackbar.LENGTH_SHORT).show()
        }

        if (this.game.getJugador(this.game.tablero.turno).nombre.equals(this.nombre)){
            game.realizaAccion(AccionMover(this, m))
        } else{
            Snackbar.make(this as View,"No te toca mover a ti", Snackbar.LENGTH_SHORT).show()
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


}
