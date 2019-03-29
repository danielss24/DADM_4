package com.example.cuatroenraya.activities

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.utility.update
import kotlinx.android.synthetic.main.fragment_round_list.*

// TODO: Rename parameter arguments, choose names that match
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
class RoundListFragment : Fragment() {
    //lateinit var recyclerView: RecyclerView
    var listener: OnRoundListFragmentInteractionListener? = null

    interface OnRoundListFragmentInteractionListener {
        fun onRoundSelected(round: Round)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnRoundListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() +
                    " must implement OnRoundListFragmentInteractionListener")
        }
    }
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun onRoundSelected(round: Round) {
        val intent = Ingame.newIntent(context!!, round.stringTablero)
        startActivity(intent)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_round_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
            update { round -> onRoundSelected(round) }
        }
    }

}
