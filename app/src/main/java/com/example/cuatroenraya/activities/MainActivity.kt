package com.example.cuatroenraya.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.example.cuatroenraya.R
import es.uam.eps.multij.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private val SIZE = 3
    private lateinit var game: Partida
    private lateinit var board: Tablero

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
    );

    private val listener = View.OnClickListener {
            view -> view.setBackgroundResource(R.drawable.casilla_vacia_24dpfilled)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ingame)
        //registerListeners()
        startRound()
    }
    private fun registerListeners() {
        var button: ImageButton
        for (i in 0 until ids.size)
            for (j in 0 until ids.size) {
                button = findViewById(ids[i][j])
                button.setOnClickListener(listener)
            }
    }
    private fun startRound() {
        val players = ArrayList<Jugador>()
        val randomPlayer = JugadorAleatorio("Random player")
        val localPlayer = LocalPlayer()
        players.add(randomPlayer)
        players.add(localPlayer)
        board = TableroCon(SIZE)
        game = Partida(board, players)
        game.addObservador(this)
        localPlayer.setPartida(game)
        registerListeners(localPlayer)
        if (game.tablero.estado == Tablero.EN_CURSO)
            game.comenzar()
    }
    fun updateUI() {
        for (i in 0 until ids.size)
            for (j in 0 until ids.size) {
                val button = findViewById(ids[i][j]) as ImageButton
                button.update(board, i, j)
            }
    }
    override fun onCambioEnPartida(evento: Evento) {
        when (evento.tipo) {
            Evento.EVENTO_CAMBIO -> updateUI()
            Evento.EVENTO_FIN -> {
                Snackbar.make(findViewById(R.id.round_title),
                    "Game over", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
