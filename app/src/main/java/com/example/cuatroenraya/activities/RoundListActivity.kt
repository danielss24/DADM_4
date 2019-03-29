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

class RoundListActivity : AppCompatActivity() {
    fun onRoundSelected(round: Round) {
        val intent = Intent(this, Ingame::class.java)
        intent.putExtra("STRINGTABLERO",round.stringTablero)
        Snackbar.make(recyclerView, "${round.title} selected",Snackbar.LENGTH_SHORT).show()
        startActivity(intent)

    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_list)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            itemAnimator = DefaultItemAnimator()
            if (adapter == null)
                adapter = RoundAdapter(RoundRepository.rounds)
                { round -> onRoundSelected(round) }
            else
                adapter?.notifyDataSetChanged()
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games_list)
        val fm = supportFragmentManager
        //val fragment = RoundListFragment()
        //fm.beginTransaction().add(R.id.fragment_container, fragment).commit()
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