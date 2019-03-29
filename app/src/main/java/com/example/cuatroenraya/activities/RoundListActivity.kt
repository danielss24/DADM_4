package com.example.cuatroenraya.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.cuatroenraya.model.RoundRepository
import kotlinx.android.synthetic.main.activity_round_list.*
import android.support.design.widget.Snackbar
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.utility.executeTransaction
import com.example.cuatroenraya.R
import kotlinx.android.synthetic.main.activity_twopane.*
import kotlinx.android.synthetic.main.fragment_round_list.*

class RoundListActivity : AppCompatActivity(), RoundListFragment.OnRoundListFragmentInteractionListener,RoundFragment.OnRoundFragmentInteractionListener {
    override fun onRoundSelected(round: Round) {
        val fm = supportFragmentManager
        if (detail_fragment_container == null) {
            startActivity(Ingame.newIntent(this, round.id))
        } else {
            fm.executeTransaction { replace(R.id.detail_fragment_container,
                RoundFragment.newInstance(round.id)) }
        }
    }
    override fun onRoundUpdated() {
        round_recycler_view.adapter!!.notifyDataSetChanged()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masterdetail)
        val fm = supportFragmentManager
        if (fm.findFragmentById(R.id.fragment_container) == null) {
            fm.executeTransaction { add(R.id.fragment_container, RoundListFragment()) }
        }
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    fun updateUI() {
        recyclerView.apply {
            if (adapter == null) {
                adapter =
                    RoundAdapter(RoundRepository.rounds)
                    { round -> onRoundSelected(round) }
            } else {
                adapter?.notifyDataSetChanged()
            }
        }
    }
}