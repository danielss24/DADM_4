package com.example.cuatroenraya.firebase

import android.app.Application
import android.support.design.widget.Snackbar
import android.util.Log
import android.widget.Toast
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.model.RoundRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FBDataBase: RoundRepository {
    private val DATABASENAME = "partidas"
    lateinit var db: DatabaseReference
    override fun open() {
        db = FirebaseDatabase.getInstance().reference.child(DATABASENAME)
    }
    override fun close() {
        db.database.goOffline()
    }
    override fun login(playername: String, password: String,callback: RoundRepository.LoginRegisterCallback) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(playername, password).addOnCompleteListener{
                task ->
            if (task.isSuccessful) {
                callback.onLogin(FirebaseAuth.getInstance().currentUser!!.uid)
            } else {
                callback.onError(playername)
            }
        }

    }
    override fun register(playername: String, password: String,
                          callback: RoundRepository.LoginRegisterCallback) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(playername,password).addOnCompleteListener(){
            task ->
            if (task.isSuccessful){
                callback.onLogin(FirebaseAuth.getInstance().currentUser!!.uid)
            } else {
                callback.onError("Error de inicio de sesion.")
            }
        }

    }
    override fun getRounds(playeruuid: String,
                           orderByField: String,
                           group: String,
                           callback: RoundRepository.RoundsCallback) {
        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("DEBUG", p0.toString())
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var rounds = listOf<Round>()
                for (postSnapshot in dataSnapshot.children) {
                    val round = postSnapshot.getValue(Round::class.java)!!
                    if (isOpenOrIamIn(round))
                        if(round.board.estado == 1){
                            rounds += round
                        }
                }
                callback.onResponse(rounds)
            }
        })
    }

    fun isOpenOrIamIn(round: Round) : Boolean{

        if(round.secondPlayerUUID == FirebaseAuth.getInstance().currentUser!!.uid || (round.firstPlayerUUID == FirebaseAuth.getInstance().currentUser!!.uid))
            return true

        return false
    }

    override fun addRound(round: Round, callback: RoundRepository.BooleanCallback) {

        var task = db.child(round.id).setValue(round)

        task.addOnCompleteListener {
            if (it.isSuccessful) {
                callback.onResponse(true)
            } else {
                callback.onResponse(false)
            }
        }

    }
    override fun updateRound(round: Round, callback: RoundRepository.BooleanCallback) {

        var task = db.child(round.id).setValue(round)

        task.addOnCompleteListener {
            if (it.isSuccessful) {
                callback.onResponse(true)
            } else {
                callback.onResponse(false)
            }
        }
    }

    fun startListeningChanges(callback: RoundRepository.RoundsCallback) {
        db = FirebaseDatabase.getInstance().getReference().child(DATABASENAME)
        db.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("DEBUG", p0.toString())
            }
            override fun onDataChange(p0: DataSnapshot) {
                var rounds = listOf<Round>()
                for (postSnapshot in p0.children)
                    rounds += postSnapshot.getValue(Round::class.java)!!
                callback.onResponse(rounds)
            }
        })
    }
    fun startListeningBoardChanges(callback: RoundRepository.RoundsCallback){

    }
}