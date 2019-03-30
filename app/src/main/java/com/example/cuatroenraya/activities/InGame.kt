package com.example.cuatroenraya.activities

import android.content.Context
import java.io.FileNotFoundException
import java.io.File
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.example.cuatroenraya.model.JugadorConecta4
import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.model.TableroConecta4
import com.example.cuatroenraya.utility.update
import es.uam.eps.multij.*
import com.example.cuatroenraya.model.RoundRepository
import java.nio.file.Files
import java.util.*
import android.widget.EditText
import com.example.cuatroenraya.utility.executeTransaction


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


    /*fun guardarPartida(View: View){
        val mEdit: EditText
        mEdit = findViewById(R.id.textoPartida) as EditText
        RoundRepository.addRound(Round(board.tableroToString(), mEdit.text.toString()))
        Snackbar.make(View,"Guardado", Snackbar.LENGTH_LONG).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }*/

}


