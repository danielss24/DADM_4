package com.example.cuatroenraya.activities

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*

import com.example.cuatroenraya.R
import com.example.cuatroenraya.firebase.FBDataBase
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.model.RoundRepository
import com.example.cuatroenraya.model.RoundRepositoryFactory
import com.example.cuatroenraya.utility.update
import kotlinx.android.synthetic.main.fragment_round_list.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RoundListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
/**
 * @brief objeto de partidas guardadas
 */
class RoundListFragment : Fragment() {
    //lateinit var recyclerView: RecyclerView
    var listener: OnRoundListFragmentInteractionListener? = null

    interface OnRoundListFragmentInteractionListener {
        fun onRoundSelected(round: Round)
        fun onPreferenceSelected()
        fun onRoundAdded()
    }

    /**
     * @brief funcion creadora de opciones
     * @param menu menu del fragmento
     * @param inflater menu inflater
     */
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu, menu)
    }

    /**
     * @brief funcion selectora de opciones
     * @param item item del menu
     * @return true o false
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_item_new_round -> {
                listener?.onRoundAdded()
                return true
            }
            R.id.menu_item_settings -> {
                listener?.onPreferenceSelected()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     * @brief funcion creadora de vista/controlador
     * @param savedInstanceState vista
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    /**
     * @brief base function
     * @param context cambio de vista
     */
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnRoundListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() +
                    " must implement OnRoundListFragmentInteractionListener")
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
     * @brief seleccion de partidas guardadas
     * @param round partida guardada
     */
    fun onRoundSelected(round: Round) {
        val intent = Ingame.newIntent(context!!, round.board.tableroToString())
        startActivity(intent)

    }

    /**
     * @brief creadora de vista
     * @param inflater inflater layout
     * @param container contenedor de vista
     * @param savedInstanceState vista
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_round_list, container, false)
    }

    /**
     * @brief creadora de vistas
     * @param view vista
     * @param savedInstanceState vista
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            update(SettingsActivity.getPlayerUUID(context!!))
            { round -> listener?.onRoundSelected(round) }
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerView.update(SettingsActivity.getPlayerUUID(context!!))
        { round -> listener?.onRoundSelected(round) }
    }


}
