package com.example.cuatroenraya.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.Toolbar
import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.RoundRepository
import kotlinx.android.synthetic.main.activity_round_list.*
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.utility.executeTransaction
import kotlinx.android.synthetic.main.activity_fragment.*
import kotlinx.android.synthetic.main.activity_twopane.*

class RoundListActivity : AppCompatActivity(),
    RoundListFragment.OnRoundListFragmentInteractionListener,
    RoundFragment.OnRoundFragmentInteractionListener{

    override fun onRoundAdded() {
        RoundRepository.addRound()
    }

    override fun onRoundUpdated() {
        recyclerView.adapter!!.notifyDataSetChanged()
    }

    override fun onRoundSelected(round: Round) {
        val fm = supportFragmentManager
        if (detail_fragment_container == null) {
            startActivity(Ingame.newIntent(this, round.id))
        } else {
            fm.executeTransaction { replace(R.id.detail_fragment_container,
                RoundFragment.newInstance(round.id)) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masterdetail)
        val fm = supportFragmentManager
        if (fm.findFragmentById(R.id.fragment_container) == null) {
            fm.executeTransaction { add(R.id.fragment_container, RoundListFragment()) }
        }
        val toolbar = findViewById<android.support.v7.widget.Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)
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