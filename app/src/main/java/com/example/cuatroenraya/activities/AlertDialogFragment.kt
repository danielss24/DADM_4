package com.example.cuatroenraya.activities

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.content.DialogInterface
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.RoundRepository

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
            RoundRepository.addRound()
            if (activity is RoundListActivity)
                activity.onRoundUpdated()
            else
                activity?.finish()
            dialog.dismiss()
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