package com.example.cuatroenraya.activities

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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
class RoundFragment : Fragment(), PartidaListener {
    private lateinit var game: Partida
    private lateinit var round: Round

    var listener: OnRoundFragmentInteractionListener? = null
    interface OnRoundFragmentInteractionListener {
        fun onRoundUpdated()
    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnRoundFragmentInteractionListener)
            listener = context
        else {
            throw RuntimeException(context.toString() +
                    " must implement OnRoundFragmentInteractionListener")
        }
    }
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_round, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        round_title.text = round.title
    }

    override fun onStart() {
        super.onStart()
        startRound()
    }

    override fun onResume() {
        super.onResume()
        view?.update(round)
    }

    internal fun startRound() {
        val players = ArrayList<Jugador>()
        val localPlayer = JugadorConecta4("Local player")
        val randomPlayer = JugadorAleatorio("Random player")
        players.add(randomPlayer)
        players.add(localPlayer)
        game = Partida(round.board, players)
        game.addObservador(this)
        localPlayer.setPartida(game)
        view?.setPlayerAsOnClickListener(localPlayer)
        if (game.tablero.estado == Tablero.EN_CURSO)
            game.comenzar()
    }

    override fun onCambioEnPartida(evento: Evento) {
        when (evento.tipo) {
            Evento.EVENTO_CAMBIO -> {
                view?.update(round)
                listener?.onRoundUpdated()
            }
            Evento.EVENTO_FIN -> {
                view?.update(round)
                listener?.onRoundUpdated()
                Snackbar.make(view!!, "Game over", Snackbar.LENGTH_SHORT).show()
            }
        }
    }




}
