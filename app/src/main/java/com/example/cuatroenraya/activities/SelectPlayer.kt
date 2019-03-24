package com.example.cuatroenraya.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.cuatroenraya.R

class SelectPlayer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_player)

        val buttonBack : ImageButton = findViewById(R.id.backButton)
        buttonBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val buttonOnePlayer : Button = findViewById(R.id.onePlayerButton)
        buttonOnePlayer.setOnClickListener{
            val intent = Intent(this, Ingame::class.java)
            startActivity(intent)
        }


    }
}
