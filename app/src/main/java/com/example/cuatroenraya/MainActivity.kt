package com.example.cuatroenraya

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import kotlinx.android.synthetic.main.principal.*




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

    /*fun onClick(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ingame)
    }

    fun cambiarPantallaTest() {
        setContentView(R.layout.ingame)
    }*/

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.getItemId()

        if (id == R.id.action_settings) {
            return true
            // lo ideal aquí sería hacer un intent para abrir una nueva clase como lo siguiente
            Log.i("ActionBar", "Settings!")
            val about = Intent(applicationContext, About::class.java)
            startActivity(about)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    */
}
