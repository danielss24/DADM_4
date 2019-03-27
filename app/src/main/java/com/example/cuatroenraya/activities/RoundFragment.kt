package com.example.cuatroenraya.activities

import android.content.Context
import android.content.Intent
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

import kotlinx.android.synthetic.main.ingame.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RoundFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class RoundFragment : Fragment(), PartidaListener  {

    val BOARDSTRING = "com.example.cuatroenraya.grid"
    private var size = 3
    private lateinit var round: Round
    private lateinit var game: Partida

    private lateinit var board: TableroConecta4



    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            round = RoundRepository.getRound(it.getString(ARG_ROUND_ID))
            //board = board
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

    companion object {
        val ARG_ROUND_ID = "com.example.cuatroenraya.roundid"
        @JvmStatic
        fun newInstance(round_id: String) =
            RoundFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ROUND_ID, round_id)
                }
            }
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
        val players = java.util.ArrayList<Jugador>()
        val randomPlayer = JugadorAleatorio("Random player")
        val localPlayer = JugadorConecta4("Local player")
        players.add(localPlayer)
        players.add(randomPlayer)
        board = TableroConecta4()
        //board.stringToTablero(cargarIntent)
        game = Partida(board, players)
        game.addObservador(this)
        view?.setPlayerAsOnClickListener(localPlayer)
        if (game.tablero.estado == Tablero.EN_CURSO)
            game.comenzar()
    }



    override fun onCambioEnPartida(evento: Evento) {
        when (evento.tipo) {
            Evento.EVENTO_CAMBIO -> {
                view?.update(round)
            }
            Evento.EVENTO_FIN -> {
                view?.update(round)
                //Aquí no puedo llamar al Intent no se por que
                Snackbar.make(view!!, "Game over", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

}
