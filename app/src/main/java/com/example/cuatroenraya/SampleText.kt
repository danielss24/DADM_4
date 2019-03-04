package com.example.cuatroenraya

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SampleText : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_text)

        val txtHello : TextView = findViewById(R.id.txt_hello)
        txtHello.setText("Joder a ver si ahora si")
    }
}
