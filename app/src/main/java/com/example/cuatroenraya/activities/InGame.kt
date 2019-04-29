package com.example.cuatroenraya.activities

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.utility.executeTransaction
import kotlinx.android.synthetic.main.activity_fragment.*

/**
 * @brief Clase de vista tablero
 */
class Ingame : AppCompatActivity(),RoundFragment.OnRoundFragmentInteractionListener {
    /**
     * @brief funcion creadora de vista/controlador
     * @param savedInstanceState vista
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        val fm = supportFragmentManager
        /*
         * Esto es un solo fragmento
         */
        if (fm.findFragmentById(R.id.fragment_container) == null) {
            val fragment = RoundFragment.newInstance(intent.getStringExtra(ROUND_ID))
            fm.executeTransaction { add(R.id.fragment_container, fragment) }
        }
        // my_toolbar is defined in the layout file
        setSupportActionBar(my_toolbar)
        // Enable the Up button from the support ActionBar corresponding to this toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    companion object {
        val ROUND_ID = "ROUND_ID"
        fun newIntent(package_context: Context, round_id: String): Intent {
            val intent = Intent(package_context, Ingame::class.java)
            intent.putExtra(ROUND_ID, round_id)
            return intent
        }
    }
    override fun onRoundUpdated(round: Round) {

    }

    /**
     * @brief carga el tema correcto
     */
    override fun getTheme(): Resources.Theme {
        val theme = super.getTheme()

        /*Preferencias*/
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
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


