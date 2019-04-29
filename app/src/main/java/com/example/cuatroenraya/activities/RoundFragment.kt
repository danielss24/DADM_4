package com.example.cuatroenraya.activities

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.JugadorConecta4
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.model.RoundRepository
import com.example.cuatroenraya.model.TableroConecta4
import com.example.cuatroenraya.utility.update
import com.example.cuatroenraya.views.ERView
import es.uam.eps.multij.*
import kotlinx.android.synthetic.main.fragment_round.*


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
        fun onRoundUpdated()
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
            throw RuntimeException(context.toString() +
                    " must implement OnRoundFragmentInteractionListener")
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
    }

    /**
     * @brief funcion iniciadora de partidas guardadas
     */
    override fun onStart() {
        super.onStart()
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
            if (round.board.getEstado() !== Tablero.EN_CURSO) {
                Snackbar.make(view as View,
                    R.string.round_already_finished, Snackbar.LENGTH_SHORT).show()
                return@OnClickListener
            }
            var tableroTMP = TableroConecta4()
            round.board.stringToTablero(tableroTMP.tableroToString())
            listener?.onRoundUpdated()
            board_erview.invalidate()
            Snackbar.make(view as View, R.string.round_restarted,
                Snackbar.LENGTH_SHORT).show()
        })

    }

    /**
     * @brief inicia la vista con todos los jugadores, tablero y clickers
     */
    internal fun startRound() {
        val players = ArrayList<Jugador>()
        val localPlayer = JugadorConecta4("Local player")
        val randomPlayer = JugadorAleatorio("Random player")
        players.add(randomPlayer)
        players.add(localPlayer)
        game = Partida(round.board, players)
        game.addObservador(this)
        localPlayer.setPartida(game)

        board_erview = view!!.findViewById(R.id.board_erview) as ERView
        board_erview.setBoard(round.board)
        board_erview.setOnPlayListener(localPlayer)

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
                //view?.update(round)
                listener?.onRoundUpdated()
            }
            Evento.EVENTO_FIN -> {
                board_erview.invalidate()
                //view?.update(round)
                listener?.onRoundUpdated()
                //Snackbar.make(view!!, "Game over", Snackbar.LENGTH_SHORT).show()
                AlertDialogFragment().show(activity?.supportFragmentManager,"ALERT_DIALOG")
            }
        }
    }


}
