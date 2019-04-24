package com.example.cuatroenraya.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.example.cuatroenraya.model.RoundRepositoryFactory
import com.example.cuatroenraya.utility.update
import kotlinx.android.synthetic.main.fragment_round_list.*

/**
 * @brief objeto de partidas guardadas
 */
class RoundListActivity : AppCompatActivity(),
    RoundListFragment.OnRoundListFragmentInteractionListener,
    RoundFragment.OnRoundFragmentInteractionListener{
    override fun onRoundAdded() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPreferenceSelected() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * @brief añadir partidas guardadas
     */
//    override fun onRoundAdded() {
//        RoundRepository.addRound()
//    }
    override fun onRoundUpdated() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    /**
     * @brief actualizar partidas guardadas
     */
    fun onRoundUpdated(round: Round) {
        val repository = RoundRepositoryFactory.createRepository(this)
        val callback = object : RoundRepository.BooleanCallback {
            override fun onResponse(response: Boolean) {
                if (response == true) {
                    recyclerView.update(
                        SettingsActivity.getPlayerUUID(baseContext),
                        { round -> onRoundSelected(round) }
                    )
                } else
                    Snackbar.make(
                        findViewById(R.id.title),
                        "R.string.error_updating_round",
                        Snackbar.LENGTH_LONG
                    ).show()
            }
        }
        repository?.updateRound(round, callback)
    }

    /**
     * @brief seleccion de partidas
     * @param round partidas guardadas
     */
    override fun onRoundSelected(round: Round) {
        if (detail_fragment_container == null) {
            startActivity(Ingame.newIntent(this, round.toJSONString()))
        } else {
            supportFragmentManager.executeTransaction {
                replace(
                    R.id.detail_fragment_container,
                    RoundFragment.newInstance(round.toJSONString())
                )
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
//        updateUI()
    }

    /**
     * @brief actualiza vista de partidas
     */
//    fun updateUI() {
//        recyclerView.apply {
//            if (adapter == null) {
//                adapter = RoundAdapter(rounds){ round -> onRoundSelected(round) }
//            } else {
//                adapter?.notifyDataSetChanged()
//            }
//        }
//    }

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

    fun onNewRoundAdded() {
        val round = Round(SettingsActivity.getBoardSize(this).toInt())
        round.firstPlayerName = "Random"
        round.firstPlayerUUID = "Random"
        round.secondPlayerName = SettingsActivity.getPlayerName(this)
        round.secondPlayerUUID = SettingsActivity.getPlayerUUID(this)
        val repository = RoundRepositoryFactory.createRepository(this)
        val callback = object : RoundRepository.BooleanCallback {
            override fun onResponse(response: Boolean) {
                if (response == false)
                    Snackbar.make(
                        findViewById(R.id.recyclerView),
                        "R.string.error_adding_round", Snackbar.LENGTH_LONG
                    ).show()
                else {
                    Snackbar.make(
                        findViewById(R.id.recyclerView),
                        "New " + round.title + " added", Snackbar.LENGTH_LONG
                    ).show()
                    val fragmentManager = supportFragmentManager
                    val roundListFragment =
                        fragmentManager.findFragmentById(R.id.fragment_container)
                                as RoundListFragment
                    roundListFragment.recyclerView.update(
                        SettingsActivity.getPlayerUUID(baseContext), { round -> onRoundSelected(round) }
                    )
                }
            }
        }
        repository?.addRound(round, callback)
    }

}