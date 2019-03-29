package com.example.cuatroenraya.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.RoundRepository
import kotlinx.android.synthetic.main.activity_round_list.*
import android.support.design.widget.Snackbar
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.utility.executeTransaction
import kotlinx.android.synthetic.main.activity_games_list.*

class RoundListActivity : AppCompatActivity(),RoundListFragment.OnRoundListFragmentInteractionListener {

    override fun onRoundSelected(round: Round) {
        val fm = supportFragmentManager
        if (fragment_detail_container == null) {
            startActivity(Ingame.newIntent(this, round.stringTablero))
        } else {
            fm.executeTransaction { replace(R.id.fragment_detail_container,
                RoundFragment.newInstance(round.stringTablero)) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games_list)
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