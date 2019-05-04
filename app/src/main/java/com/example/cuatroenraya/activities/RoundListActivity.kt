package com.example.cuatroenraya.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.View
import android.widget.Toolbar
import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.RoundRepository
import kotlinx.android.synthetic.main.activity_round_list.*
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.utility.executeTransaction
import kotlinx.android.synthetic.main.activity_fragment.*
import kotlinx.android.synthetic.main.activity_twopane.*
import android.widget.Button
import com.example.cuatroenraya.firebase.FBDataBase
import com.example.cuatroenraya.model.RoundRepositoryFactory
import com.example.cuatroenraya.utility.update
import kotlinx.android.synthetic.main.fragment_round_list.*
import java.lang.Exception

/**
 * @brief objeto de partidas guardadas
 */
class RoundListActivity : AppCompatActivity(),
    RoundListFragment.OnRoundListFragmentInteractionListener,
    RoundFragment.OnRoundFragmentInteractionListener{

    /**
     * @brief Funcion para cuando añadimos una nueva partida
     */
    override fun onRoundAdded() {
        onNewRoundAdded()
    }

    /**
     * @brief Funcionm para lanzar el menu de opciones cuando la preferencia ha sido seleccionada
     */
    override fun onPreferenceSelected() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    /**
     * @brief actualizar partidas guardadas
     * @param round partida que se ha actulizado
     */
    override fun onRoundUpdated(round: Round) {
        val repository = RoundRepositoryFactory.createRepository(this)
        val callback = object : RoundRepository.BooleanCallback {
            override fun onResponse(response: Boolean) {
                if (response == true) {
                    recyclerView.update(
                        SettingsActivity.getPlayerUUID(baseContext),
                        { round -> onRoundSelected(round) }
                    )
                } else
                    Snackbar.make(findViewById(R.id.title),resources.getString(R.string.error_updating_round),Snackbar.LENGTH_LONG).show()
            }
        }
        repository?.updateRound(round, callback)
    }

    /**
     * @brief seleccion de partidas
     * @param round partida guardada
     */
    override fun onRoundSelected(round: Round) {
        if (detail_fragment_container == null) {
            startActivity(Ingame.newIntent(this, round.toJSONString()))
        } else {
            supportFragmentManager.executeTransaction {
                replace(R.id.detail_fragment_container,RoundFragment.newInstance(round.toJSONString()))
            }
        }
    }

    /**
     * @brief funcion creadora de vista/controlador
     * @param savedInstanceState vista
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masterdetail)
        val fm = supportFragmentManager
        if (fm.findFragmentById(R.id.fragment_container) == null) {
            fm.executeTransaction { add(R.id.fragment_container, RoundListFragment()) }
        }
        val toolbar = findViewById<android.support.v7.widget.Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)

    }

    /**
     * @brief funcion reanudar de vista
     */
    override fun onResume() {
        super.onResume()

        recyclerView.update(SettingsActivity.getPlayerUUID(baseContext),
        { round -> onRoundSelected(round)})
    }

    /**
     * @brief Funcion para comenzar una ronda
     * @param round Partida a comenzar
     */
    private fun startRound(round: Round) {
        val fm = supportFragmentManager
        if (findViewById<View>(R.id.detail_fragment_container) == null) {
            val intent = Ingame.newIntent(baseContext, round.toJSONString())
            startActivity(intent)
        } else {
            fm.executeTransaction {
                replace(
                    R.id.detail_fragment_container,
                    RoundFragment.newInstance(round.toJSONString())
                )
            }
        }
    }

    /**
     * @brief Funcion para añadir una nueva ronda a la lista de partidas y la base de datos
     */
    fun onNewRoundAdded() {
        val round = Round()
        round.firstPlayerName = SettingsActivity.getPlayerName(this)
        round.firstPlayerUUID = SettingsActivity.getPlayerUUID(this)
        round.secondPlayerName = resources.getString(R.string.jugador_OPEN)
        round.secondPlayerUUID = resources.getString(R.string.jugador_OPEN)

        val repository = RoundRepositoryFactory.createRepository(this)

        val callback = object : RoundRepository.BooleanCallback {
            override fun onResponse(response: Boolean) {
                if (response == false) {
                    Snackbar.make(findViewById(R.id.recyclerView),resources.getString(R.string.error_adding_round), Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(findViewById(R.id.recyclerView),"New " + round.title + " added", Snackbar.LENGTH_LONG).show()
                    val fragmentManager = supportFragmentManager
                    val roundListFragment =fragmentManager.findFragmentById(R.id.fragment_container)as RoundListFragment
                    roundListFragment.recyclerView.update(SettingsActivity.getPlayerUUID(baseContext), { round -> onRoundSelected(round) }
                    )
                }
            }
        }
        repository?.addRound(round, callback)
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