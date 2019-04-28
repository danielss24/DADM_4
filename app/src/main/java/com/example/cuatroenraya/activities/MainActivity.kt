package com.example.cuatroenraya.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.preference.PreferenceManager
import android.widget.Button
import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.RoundRepository
import android.content.SharedPreferences



/**
 * @brief Clase principal de aplicacion
 */
class MainActivity : AppCompatActivity() {
    /**
     * @brief funcion creadora de vista/controlador
     * @param savedInstanceState vista
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
        // enter the key from your xml and the default value
        val value = sharedPreferences.getBoolean("theme_switch", false)

        if(value){
            setTheme(R.style.AppTheme_Bl)
        }else if(!value){
            setTheme(R.style.AppTheme)
        }
        setContentView(R.layout.principal)

        val buttonPlay : Button = findViewById(R.id.buttonLoadGame)
        buttonPlay.setOnClickListener{
            val intent = Intent(this, RoundListActivity::class.java)
            startActivity(intent)
        }

        val buttonExit : Button =  findViewById(R.id.buttonExit)
        buttonExit.setOnClickListener{
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
        }

        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false)



    }


}
