package com.example.cuatroenraya

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import kotlinx.android.synthetic.main.principal.*
import android.view.View
import android.widget.ImageButton
import es.uam.eps.multij.*
import java.util.ArrayList
import android.util.Log


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.principal)

        val buttonNewGame : Button = findViewById(R.id.buttonNewGame)

        buttonNewGame.setOnClickListener{
            val intent = Intent(this, SelectPlayer ::class.java)
            startActivity(intent)
        }

    }


}
