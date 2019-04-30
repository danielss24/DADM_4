package com.example.cuatroenraya.activities

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.content.DialogInterface
import android.content.Intent
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.model.RoundRepository
import com.example.cuatroenraya.model.RoundRepositoryFactory
import com.example.cuatroenraya.utility.update
import kotlinx.android.synthetic.main.fragment_round_list.*

/**
 * @brief Clase de alert para fragmentos
 */
class AlertDialogFragment : DialogFragment() {
    /**
     * @brief funcion creadora de vista/controlador
     * @param savedInstanceState vista
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = activity as AppCompatActivity?
        val alertDialogBuilder = AlertDialog.Builder(getActivity())
        alertDialogBuilder.setTitle(R.string.finPartida)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity())
        val playerName = sharedPreferences.getString("playerName", "Jugador")
        var mensaje =  getString(R.string.game_over_message) + " " + playerName + "?"

        alertDialogBuilder.setMessage(mensaje)
        alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
            val round = Round()
            round.firstPlayerName = "Random"
            round.firstPlayerUUID = "Random"
            round.secondPlayerName = SettingsActivity.getPlayerName(context!!)
            round.secondPlayerUUID = SettingsActivity.getPlayerUUID(context!!)

            val repository = RoundRepositoryFactory.createRepository(context!!)

            val callback = object : RoundRepository.BooleanCallback {
                override fun onResponse(response: Boolean) {
                    if (response == false) {
                        //Snackbar.make(findViewById(R.id.recyclerView),"R.string.error_adding_round", Snackbar.LENGTH_LONG).show()
                    } else {
                       /* Snackbar.make(findViewById(R.id.recyclerView),"New " + round.title + " added", Snackbar.LENGTH_LONG).show()
                        val fragmentManager = supportFragmentManager
                        val roundListFragment =fragmentManager.findFragmentById(R.id.fragment_container)as RoundListFragment
                        roundListFragment.recyclerView.update(SettingsActivity.getPlayerUUID(baseContext), { round -> onRoundSelected(round) }
                        )*/
                    }
                }
            }
            repository?.addRound(round, callback)
            val intent = Intent(context!!, RoundListActivity::class.java)
            startActivity(intent)
        }
        alertDialogBuilder.setNegativeButton("No",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    if (activity is Ingame)
                        activity?.finish()
                    dialog.dismiss()
                }
            })
        return alertDialogBuilder.create()
    }
}