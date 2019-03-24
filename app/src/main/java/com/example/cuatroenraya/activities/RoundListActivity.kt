package com.example.cuatroenraya.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.RoundRepository
import kotlinx.android.synthetic.main.activity_round_list.*
import android.support.design.widget.Snackbar
import com.example.cuatroenraya.model.Round


class RoundListActivity : AppCompatActivity() {
    fun onRoundSelected(round: Round) {
        Snackbar.make(
            recyclerView, "${round.title} selected",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_list)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            itemAnimator = DefaultItemAnimator()
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