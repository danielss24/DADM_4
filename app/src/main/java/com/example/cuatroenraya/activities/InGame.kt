package com.example.cuatroenraya.activities

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import com.example.cuatroenraya.R
import com.example.cuatroenraya.firebase.FBDataBase
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.model.RoundRepository
import com.example.cuatroenraya.model.RoundRepositoryFactory
import com.example.cuatroenraya.utility.executeTransaction
import com.example.cuatroenraya.utility.update
import kotlinx.android.synthetic.main.activity_fragment.*
import kotlinx.android.synthetic.main.fragment_round_list.*

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

    /**
     * Companion object
     */
    companion object {
        val ROUND_ID = "ROUND_ID"
        fun newIntent(package_context: Context, round_id: String): Intent {
            val intent = Intent(package_context, Ingame::class.java)
            intent.putExtra(ROUND_ID, round_id)
            return intent
        }
    }

    /**
     * @brief funcion para actualizar la base de datos cuando se produce un cambio en la aprtida
     * @param round Partida en la que se producer el cambio
     */
    override fun onRoundUpdated(round: Round) {
        val repository = RoundRepositoryFactory.createRepository(this)
        val callback = object : RoundRepository.BooleanCallback {
            override fun onResponse(response: Boolean) {
                if (response != true) {
                    Snackbar.make(findViewById(R.id.title),R.string.error_updating_round,Snackbar.LENGTH_LONG).show()
                }
            }
        }
        cargarCorrectamentePartida(repository!!,round)
        repository?.updateRound(round, callback)
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

    /**
     * @brief carga la partida de  manera correcta respecto a la base de datos online
     * @param repository repositorio actual
     * @param round partida que vamos a actualizar
     */
    fun cargarCorrectamentePartida(repository: RoundRepository, round: Round){
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(SettingsActivity.ONLINE, false) == true){
            if (repository is FBDataBase){
                if (true == repository.isOpenOrIamIn(round)){
                    if (round.firstPlayerName != SettingsActivity.getPlayerName(this)){
                        round.secondPlayerName = SettingsActivity.getPlayerName(this)
                        round.secondPlayerUUID = SettingsActivity.getPlayerUUID(this)
                    }
                }
            }
        }
        var numEmpty = 0
        var numFirst = 0
        var numSecond = 0
        for (col in 0..(round.board.NUM_COL-1)){
            for(fil in 0 ..(round.board.NUM_FIL-1)){
                var value = round.board.getTablero(fil,col)
                if (value == 0){
                    numFirst++
                } else if (value == 1){
                    numSecond++
                } else {
                    numEmpty++
                }
            }
        }

        if (numFirst == numSecond){
            round.board.setTurno(0)
        } else {
            round.board.setTurno(1)
        }
        round.board.numJugadas=(numFirst+numSecond)
    }
}


