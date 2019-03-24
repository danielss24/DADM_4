package com.example.cuatroenraya.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import com.example.cuatroenraya.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.principal)

        val buttonNewGame : Button = findViewById(R.id.buttonNewGame)

        buttonNewGame.setOnClickListener{
            val intent = Intent(this, SelectPlayer::class.java)
            startActivity(intent)
        }

        val buttonCargar : Button = findViewById(R.id.buttonLoadGame)
        buttonCargar.setOnClickListener{
            val intent = Intent(this, RoundListActivity::class.java)
            startActivity(intent)
        }

        val buttonoptions : Button = findViewById(R.id.buttonOptions)
        buttonoptions.setOnClickListener{
            val intent = Intent(this, Options::class.java)
            startActivity(intent)
        }

        val buttonExit : Button =  findViewById(R.id.buttonExit)
        buttonExit.setOnClickListener{
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
        }



    }


}
