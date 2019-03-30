package com.example.cuatroenraya.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.cuatroenraya.R
import com.example.cuatroenraya.utility.executeTransaction
import kotlinx.android.synthetic.main.activity_fragment.*


class Ingame : AppCompatActivity(),RoundFragment.OnRoundFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masterdetail)
        val fm = supportFragmentManager
        if (fm.findFragmentById(R.id.fragment_container) == null) {
            val fragment =
                RoundFragment.newInstance(intent.getStringExtra(ROUND_ID))
            fm.executeTransaction { add(R.id.fragment_container, fragment) }
        }
        // my_toolbar is defined in the layout file
        setSupportActionBar(my_toolbar)
        // Enable the Up button from the support ActionBar corresponding to this toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    companion object {
        val ROUND_ID = "ROUND_ID"
        fun newIntent(package_context: Context, round_id: String): Intent {
            val intent = Intent(package_context, Ingame::class.java)
            intent.putExtra(ROUND_ID, round_id)
            return intent
        }
    }
    override fun onRoundUpdated() {
    }
}


