package com.example.cuatroenraya.activities

import android.content.Intent
import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.cuatroenraya.R
import kotlinx.android.synthetic.main.principal.view.*


class ChipSelector : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chip_selector)

        var dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        var width = dm.widthPixels
        var height = dm.heightPixels
        window.setLayout((width * 0.8).toInt(), (height * 0.8).toInt())

        val buttonMnu: Button = findViewById(R.id.backMenuButton)
        buttonMnu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val buttonExit: Button = findViewById(R.id.exitButton)
        buttonExit.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
        }

        val intent = getIntent()
        val bundle = intent.extras

        bundle.getString("ganador")
        val winner = bundle.getString("ganador")

        val texttView: TextView = findViewById(R.id.mensanjeGanador)
        texttView.append("Gana: "+ winner)


    }
}
