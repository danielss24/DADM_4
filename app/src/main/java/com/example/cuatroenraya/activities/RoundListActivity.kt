package com.example.cuatroenraya.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.RoundRepository
import kotlinx.android.synthetic.main.activity_round_list.*


class RoundListActivity: AppCompatActivity() {

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
            if (adapter == null)
                adapter =
                    RoundAdapter(RoundRepository.rounds)
            else
                adapter?.notifyDataSetChanged()
        }
    }
}
