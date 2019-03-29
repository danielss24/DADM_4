package com.example.cuatroenraya.activities

import android.content.Context
import java.io.FileNotFoundException
import java.io.File
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.example.cuatroenraya.model.JugadorConecta4
import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.model.TableroConecta4
import com.example.cuatroenraya.utility.update
import es.uam.eps.multij.*
import com.example.cuatroenraya.model.RoundRepository
import java.nio.file.Files
import java.util.*
import android.widget.EditText




class Ingame : AppCompatActivity(), PartidaListener {
    val BOARDSTRING = "com.example.cuatroenraya.grid"

    private lateinit var game: Partida
    private lateinit var board: TableroConecta4

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

    private val listener = View.OnClickListener { view ->
        view.setBackgroundResource(R.drawable.casilla_vacia_24dpfilled)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ingame)
        startRound()
        updateUI()
        if (savedInstanceState != null) {
            try {
                board.stringToTablero(savedInstanceState?.getString(BOARDSTRING))
            } catch (e: ExcepcionJuego) {
                e.printStackTrace()
                //Snackbar.make(findViewById(R.id.round_title), "ExcepcionJuego thrown.",
                //Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        val TABLEROSTRING = "TABLEROSTRING"
        fun newIntent(package_context: Context, tablero: String): Intent {
            val intent = Intent(package_context, Ingame::class.java)
            intent.putExtra(TABLEROSTRING, tablero)
            return intent
        }
    }

    private fun registerListeners(jugador: JugadorConecta4) {
        var button: ImageButton
        for (i in 0 until ids.size)
            for (j in 0 until ids[i].size) {
                button = findViewById(ids[i][j])
                button.setOnClickListener(jugador)
            }
    }

    private fun startRound() {
        val players = ArrayList<Jugador>()
        val localPlayer = JugadorConecta4("Local player")
        val randomPlayer = JugadorConecta4("Random player")
        players.add(localPlayer)
        players.add(randomPlayer)
        val cargarIntent = intent.extras.getString("TABLEROSTRING")
        board = TableroConecta4()
        board.stringToTablero(cargarIntent)
        game = Partida(board, players)
        game.addObservador(this)
        localPlayer.setPartida(game)
        registerListeners(localPlayer)
        if (game.tablero.estado == Tablero.EN_CURSO)
            game.comenzar()
    }

    fun updateUI() {
        for (i in 0 until ids.size)
            for (j in 0 until ids[i].size) {
                val button = findViewById(ids[i][j]) as ImageButton
                button.update(board, i, j)
            }
    }

    override fun onCambioEnPartida(evento: Evento) {
        when (evento.tipo) {
            Evento.EVENTO_CAMBIO -> updateUI()
            Evento.EVENTO_FIN -> {
                if(this.board.estado == Tablero.FINALIZADA){
                    val intent = Intent(this, GameOver::class.java)
                    //Esto es para pasar info entre activities
                    val bundle = Bundle()
                    bundle.putString("ganador", game.getJugador(game.tablero.turno).nombre)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }else if (this.board.estado == Tablero.TABLAS){
                    val intent = Intent(this, GameOver::class.java)
                    startActivity(intent)
                }

            }
        }
    }

    private var numero: Int = 0

    private fun log(text: String) {
        Log.d(
            "LifeCycleTest", Integer.toString(numero) + " : "
                    + text
        )
        numero++
    }

    override fun onStart() {
        super.onStart()
        log("onStart")
    }

    override fun onResume() {
        super.onResume()
        log("onResume")
    }

    override fun onPause() {
        super.onPause()
        log("onPause")
    }

    override fun onStop() {
        super.onStop()
        log("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        log("onRestart")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(BOARDSTRING, board.tableroToString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        try {
            if (savedInstanceState?.getString(BOARDSTRING) != null)
                board.stringToTablero(savedInstanceState?.getString(BOARDSTRING))
            updateUI()
        } catch (e: ExcepcionJuego) {
            e.printStackTrace()
            //Snackbar.make(findViewById(R.id.round_title), "ExcepcionJuego thrown.",
            //Snackbar.LENGTH_SHORT).show()
        }
    }

    fun guardarPartida(View: View){
        val mEdit: EditText
        mEdit = findViewById(R.id.textoPartida) as EditText
        RoundRepository.addRound(Round(board.tableroToString(), mEdit.text.toString()))
        Snackbar.make(View,"Guardado", Snackbar.LENGTH_LONG).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}


