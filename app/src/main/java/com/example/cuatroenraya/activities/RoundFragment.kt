package com.example.cuatroenraya.activities

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.JugadorConecta4
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.model.RoundRepository
import com.example.cuatroenraya.model.TableroConecta4
import com.example.cuatroenraya.utility.setPlayerAsOnClickListener
import com.example.cuatroenraya.utility.update
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
        arguments?.let {
            round = RoundRepository.getRound(it.getString((ROUND_ID)))
        }
    }

    companion object {
        val ROUND_ID = "ROUND_ID"
        @JvmStatic
        fun newInstance(round_id: String) =
            RoundFragment().apply {
                arguments = Bundle().apply {
                    putString(ROUND_ID, round_id)
                }
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

    /**
     * @brief vista creadora
     * @param view vista creada
     * @param savedInstanceState vista
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        round_title.text = round.title
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
        view?.update(round)
    }

    /**
     * @brief registra clickers para fragmentos
     * @param jugador jugador local
     */
    private fun registerListeners(jugador: View.OnClickListener) {
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
                button = view!!.findViewById<View>(ids[i][j]) as ImageButton
                button.setOnClickListener(jugador)
            }

        val resetButton = view!!.findViewById(R.id.reset_round_fab) as FloatingActionButton
        resetButton.setOnClickListener(View.OnClickListener {
            if (round.board.getEstado() !== Tablero.EN_CURSO) {
                Snackbar.make(view as View,
                    R.string.round_already_finished, Snackbar.LENGTH_SHORT).show()
                return@OnClickListener
            }
            var tableroTMP = TableroConecta4()
            round.board.stringToTablero(tableroTMP.tableroToString())
            //startRound()
            listener?.onRoundUpdated()
            view?.update(round)
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
        //view?.setPlayerAsOnClickListener(localPlayer)
        registerListeners(localPlayer)
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
                view?.update(round)
                listener?.onRoundUpdated()
            }
            Evento.EVENTO_FIN -> {
                view?.update(round)
                listener?.onRoundUpdated()
                //Snackbar.make(view!!, "Game over", Snackbar.LENGTH_SHORT).show()
                AlertDialogFragment().show(activity?.supportFragmentManager,"ALERT_DIALOG")

            }
        }
    }


}
