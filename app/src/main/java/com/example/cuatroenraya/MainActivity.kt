package com.example.cuatroenraya

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ingame)
    }
    fun onButtonClicked(view: View) {
        view.setBackgroundResource(R.drawable.casilla_vacia_24dpfilled)
    }

}
