package com.example.cuatroenraya.activities

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

import com.example.cuatroenraya.R
import com.example.cuatroenraya.firebase.FBDataBase
import com.example.cuatroenraya.model.*
import com.example.cuatroenraya.utility.update
import com.example.cuatroenraya.views.ERView
import es.uam.eps.multij.*
import kotlinx.android.synthetic.main.fragment_round.*
import java.security.cert.Extension


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RoundFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
/**
 * @brief fragmento de partidas guardadas
 */
class RoundFragment : Fragment(), PartidaListener {
    private lateinit var game: Partida
    private lateinit var round: Round
    private lateinit var board_erview: ERView
    var listener: OnRoundFragmentInteractionListener? = null

    interface OnRoundFragmentInteractionListener {
        fun onRoundUpdated(round: Round)
    }

    /**
     * @brief base function
     * @param context contesto
     */
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnRoundFragmentInteractionListener)
            listener = context
        else {
            throw RuntimeException(context.toString() + " must implement OnRoundFragmentInteractionListener")
        }
    }

    /**
     * @brief base function
     */
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * @brief funcion creadora de vista/controlador
     * @param savedInstanceState vista
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            arguments?.let {
                round = Round.fromJSONString(it.getString(ARG_ROUND))
            }
        } catch (e: Exception) {
            Log.d("DEBUG", e.message)
            activity?.finish()
        }
    }

    /**
     * @brief funcion creadora de vista
     * @param inflater inflater
     * @param container contenedor de la vista
     * @param savedInstanceState vista
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_round, container, false)
    }

    companion object {
        val ARG_ROUND = "es.uam.eps.dadm.er20.round"
        val BOARDSTRING = "es.uam.eps.dadm.er20.boardstring"
        @JvmStatic
        fun newInstance(round: String) =
            RoundFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ROUND, round)
                }
            }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(BOARDSTRING, round.board.tableroToString())
        super.onSaveInstanceState(outState)
    }

    /**
     * @brief vista creadora
     * @param view vista creada
     * @param savedInstanceState vista
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        round_title.text = "${round.title}"
        if (savedInstanceState != null) {
            round.board.stringToTablero(savedInstanceState.getString(BOARDSTRING))
        }
        registerResetButton()
    }

    /**
     * @brief funcion iniciadora de partidas guardadas
     */
    override fun onStart() {
        super.onStart()
        val repository = RoundRepositoryFactory.createRepository(this.context!!)
        if (repository is FBDataBase){
            val callback = object : RoundRepository.RoundsCallback {
                override fun onResponse(rounds: List<Round>) {
                    for (ronda in rounds){
                        if (round.id == ronda.id){
                            round.board.stringToTablero(ronda.board.toString())
                            round.board.numJugadas = ronda.board.numJugadas
                            round.board.setTurno(ronda.board.turno)
                            round.board.estado = ronda.board.estado
                            round.secondPlayerUUID = ronda.secondPlayerUUID
                            round.secondPlayerName = ronda.secondPlayerName
                            round.firstPlayerUUID = ronda.firstPlayerUUID
                            round.firstPlayerName = ronda.firstPlayerName
                            board_erview.setBoard(ronda.board)
                            board_erview.invalidate()
                        }
                    }

                }
                override fun onError(error: String) {
                    print(error)
                }
            }
            repository.startListeningBoardChanges(callback)
        }
        startRound()
    }

    /**
     * @brief funcion reanudadora
     */
    override fun onResume() {
        super.onResume()
        board_erview.invalidate()
    }

    /**
     * @brief registra clickers para fragmentos
     * @param jugador jugador local
     */
    private fun registerResetButton() {
        val resetButton = view!!.findViewById(R.id.reset_round_fab) as FloatingActionButton
        resetButton.setOnClickListener(View.OnClickListener {
            if (round.board.getEstado() != Tablero.EN_CURSO) {
                Snackbar.make(view as View,R.string.round_already_finished, Snackbar.LENGTH_SHORT).show()
                return@OnClickListener
            }
            var tableroTMP = TableroConecta4()
            round.board.stringToTablero(tableroTMP.tableroToString())
            listener?.onRoundUpdated(round)
            board_erview.invalidate()
            Snackbar.make(view as View, R.string.round_restarted,Snackbar.LENGTH_SHORT).show()
        })
    }

    /**
     * @brief inicia la vista con todos los jugadores, tablero y clickers
     */
    internal fun startRound() {
        val players = ArrayList<Jugador>()
        val localPlayer = JugadorConecta4(SettingsActivity.getPlayerName(this.context!!))

        var local = true
        var player2 : Jugador
        if (PreferenceManager.getDefaultSharedPreferences(this.context).getBoolean("OnlineMode",false)==false){
            player2 = JugadorAleatorio("PC player")
            players.add(localPlayer)
            players.add(player2)
        }
        //TODO ESTO DEBERIA IR EN EL ONROUNDSELECTED
        val repository = RoundRepositoryFactory.createRepository(this.context!!)
        if (PreferenceManager.getDefaultSharedPreferences(this.context).getBoolean("OnlineMode", false) == true){
            if (repository is FBDataBase){
                if (true == repository.isOpenOrIamIn(round)){
                    //Si soy el primero, me meto y creo un segundo jugador que es NULL
                    if (round.firstPlayerName == SettingsActivity.getPlayerName(this.context!!)){
                        player2 = JugadorConecta4(round.secondPlayerName)
                        players.add(localPlayer)
                        players.add(player2)
                    } else if (round.secondPlayerName == "null"){ // Si el segundo sitio esta libre me meto yo
                        player2 = JugadorConecta4(round.firstPlayerName)
                        players.add(player2)
                        players.add(localPlayer)
                    } else if (round.secondPlayerName == SettingsActivity.getPlayerName(this.context!!)){ // Si creo yo la partida
                        player2 = JugadorConecta4(round.firstPlayerName)
                        players.add(player2)
                        players.add(localPlayer)
                    }
                }
            }
        }
        var numEmpty = 0
        var numFirst = 0
        var numSecond = 0
        for (col in 0..(round.board.NUM_COL-1)){
            for(fil in 0 ..(round.board.NUM_FIL-1)){
                var value = round.board.getTablero(fil,col)
                if (value == 0){
                    numFirst++
                } else if (value == 1){
                    numSecond++
                } else {
                    numEmpty++
                }
            }
        }

        if (numFirst == numSecond){
            round.board.setTurno(0)
        } else {
            round.board.setTurno(1)
        }
        round.board.numJugadas=(numFirst+numSecond)
        game = Partida(round.board, players)
        game.addObservador(this)
        localPlayer.setPartida(game)

        board_erview = view!!.findViewById(R.id.board_erview) as ERView
        board_erview.setBoard(round.board)
        board_erview.setOnPlayListener(localPlayer)
        round.board.cambiaEstado()

        if (game.tablero.estado == Tablero.EN_CURSO)
            game.comenzar()

    }

    /**
     * @brief funcion modificadora de partida
     * @param evento evento de cambio
     */
    override fun onCambioEnPartida(evento: Evento) {
        when (evento.tipo) {
            Evento.EVENTO_CAMBIO -> {
                board_erview.invalidate()
                listener?.onRoundUpdated(round)
            }
            Evento.EVENTO_FIN -> {
                board_erview.invalidate()
                listener?.onRoundUpdated(round)
                AlertDialogFragment().show(activity?.supportFragmentManager,"ALERT_DIALOG")
            }
        }
    }
}
