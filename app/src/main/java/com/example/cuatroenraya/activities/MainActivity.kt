package com.example.cuatroenraya.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.preference.PreferenceManager
import android.widget.Button
import com.example.cuatroenraya.R
import android.content.res.Resources
import android.content.Context




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

    /**
     * @brief carga el tema correcto
     */
    override fun getTheme(): Resources.Theme {
        val theme = super.getTheme()

        /*Preferencias*/
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
        val themeVal = sharedPreferences.getString("themes_list", "1")
        if(themeVal.toInt() == 1){
            theme.applyStyle(R.style.AppTheme, true)
        }else if(themeVal.toInt() == 2){
            theme.applyStyle(R.style.AppTheme_Blue, true)
        }else if (themeVal.toInt() == 3){
            theme.applyStyle(R.style.AppTheme_Green,true )
        }
        /*Preferencias*/

        return theme
    }
}
