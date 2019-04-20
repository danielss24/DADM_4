package com.example.cuatroenraya.activities

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*

import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.utility.executeTransaction
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
/**
 * @brief objeto de partidas guardadas
 */
class RoundListActivity : AppCompatActivity(),
    RoundListFragment.OnRoundListFragmentInteractionListener,
    RoundFragment.OnRoudFragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masterdetail)
        val fm = supportFragmentManager
        if (fm.findFragmentById(R.id.fragment_container) == null) {
            fm.executeTransaction { add(R.id.fragment_container, RoundListFragment()) }
        }
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false)
    }
    override fun onRoundUpdated() {
        round_recycler_view.adapter.notifyDataSetChanged()
    }
    override fun onRoundSelected(round: Round) {
        val fm = supportFragmentManager
        if (detail_fragment_container == null) {
            startActivity(RoundActivity.newIntent(this, round.id))
        } else {
            fm.executeTransaction { replace(R.id.detail_fragment_container,
                RoundFragment.newInstance(round.id)) }
        }
    }
    override fun onPreferenceSelected() {
        startActivity(Intent(this, ERSettingsActivity::class.java))
    }
    override fun onNewRoundAdded() {
        val round = Round(ERSettingsActivity.getBoardSize(this).toInt())
        RoundRepository.addRound(round)
    }
}
