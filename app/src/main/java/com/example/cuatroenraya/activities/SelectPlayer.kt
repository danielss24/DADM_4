package com.example.cuatroenraya.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.RoundRepository
import com.example.cuatroenraya.utility.executeTransaction
import kotlinx.android.synthetic.main.activity_twopane.*

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
            setContentView(R.layout.activity_masterdetail)
            val round_id = RoundRepository.addRound()
            val fm = supportFragmentManager
            if (detail_fragment_container == null) {
                startActivity(Ingame.newIntent(this, round_id))

            } else {
                fm.executeTransaction { replace(R.id.detail_fragment_container,
                    RoundFragment.newInstance(round_id)) }
            }
        }


    }
}
