package com.example.cuatroenraya.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.cuatroenraya.R

/**
 * @brief objeto de opciones
 */
class Options : AppCompatActivity() {
    /**
     * @brief funcion creadora de vista/controlador
     * @param savedInstanceState vista
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        val buttonBack : ImageButton = findViewById(R.id.backButton)
        buttonBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}
