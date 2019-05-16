package com.example.cuatroenraya.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
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
class AlertDialogFragmentDelete : DialogFragment() {

    lateinit var round: Round
    lateinit var activity: RoundListActivity
    val DATABASE_NAME = "conectacuatro.db"


    override fun setArguments(args: Bundle?) {
        round = Round.fromJSONString(args!!.getString("round"))
        super.setArguments(args)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = activity as AppCompatActivity?
        val alertDialogBuilder = AlertDialog.Builder(getActivity())
        alertDialogBuilder.setTitle("Confirmar")
        alertDialogBuilder.setMessage("¿Quieres borrar la partida?")
        alertDialogBuilder.setPositiveButton("Si") { dialog, which ->

            val repository = RoundRepositoryFactory.createRepository(this.context!!)
            val callback = object : RoundRepository.BooleanCallback {
                override fun onResponse(response: Boolean) {
                    if (response == false) {
                        Snackbar.make(activity!!.findViewById(R.id.recyclerView),resources.getString(R.string.error_adding_round), Snackbar.LENGTH_LONG).show()
                    } else {
                        Snackbar.make(activity!!.findViewById(R.id.recyclerView),round.title + " deleted", Snackbar.LENGTH_LONG).show()
                        val fragmentManager = savedInstanceState
                        val roundListFragment =activity.supportFragmentManager?.findFragmentById(R.id.fragment_container)as RoundListFragment
                        if (activity is RoundListActivity) {
                            roundListFragment.recyclerView.update(
                                SettingsActivity.getPlayerUUID(activity.baseContext),
                                { round -> activity.onRoundSelected(round) },
                                { round -> activity.onRoundDeleted(round) }
                            )
                        }
                    }
                }
            }

            repository?.deleteRound(round, callback)

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

    override fun onAttach(context: Context?){
        super.onAttach(context)
        activity =  context as RoundListActivity

    }
}