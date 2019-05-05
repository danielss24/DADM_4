package com.example.cuatroenraya.firebase
import android.content.res.Resources
import android.app.Application
import android.support.design.widget.Snackbar
import android.util.Log
import android.widget.Toast
import com.example.cuatroenraya.activities.SettingsActivity
import com.example.cuatroenraya.model.Round
import com.example.cuatroenraya.model.RoundRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import es.uam.eps.multij.Tablero

class FBDataBase: RoundRepository {
    private val DATABASENAME = "partidas"
    lateinit var db: DatabaseReference

    /**
     * @brief FUncion para abrir la database
     */
    override fun open() {
        db = FirebaseDatabase.getInstance().reference.child(DATABASENAME)
    }

    /**
     * @brief funcion para cerrar la base de datos
     */
    override fun close() {
        db.database.goOffline()
    }

    /**
     * @brief loggin en la base de datos
     * @param playername Nombre del jugador
     * @param password contraseña
     * @param callback Callback
     */
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

    /**
     * @brief funcion para registrarse en la base de datos
     * @param playername Nombre del jugador
     * @param password contraseña
     * @param callback Callback
     */
    override fun register(playername: String, password: String,callback: RoundRepository.LoginRegisterCallback) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(playername,password).addOnCompleteListener(){
            task ->
            if (task.isSuccessful){
                callback.onLogin(FirebaseAuth.getInstance().currentUser!!.uid)
            } else {
                callback.onError("Error de inicio de sesion.")
            }
        }

    }

    /**
     * @brief funcion para obtener las rondas de la base de datos
     * @param playeruuid ID del jugador
     * @param orderByField campo por el que ordenar
     * @param callback Callback
     */
    override fun getRounds(playeruuid: String,orderByField: String,group: String,callback: RoundRepository.RoundsCallback) {
        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("DEBUG", p0.toString())
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var rounds = listOf<Round>()
                for (postSnapshot in dataSnapshot.children) {
                    val round = postSnapshot.getValue(Round::class.java)!!
                    if (isOpenOrIamIn(round)) {
                        if (round.board.estado == Tablero.EN_CURSO) {
                            rounds += round
                        }
                    }
                }
                callback.onResponse(rounds)
            }
        })
    }

    /**
     * @brief funcion para comprobar si estamos en una partida o esta se encuentra abierta
     * @param round partida
     * @return true o false
     */
    fun isOpenOrIamIn(round: Round) : Boolean{

        if(round.secondPlayerUUID == FirebaseAuth.getInstance().currentUser!!.uid ||
            (round.firstPlayerUUID == FirebaseAuth.getInstance().currentUser!!.uid))
            return true

        if (round.secondPlayerUUID == "jugador_OPEN" || (round.firstPlayerUUID == "jugador_OPEN"))
            return true

        return false
    }

    /**
     * @brief Funcion para añadir una ronda a la base de datos
     * @param round partida a añadir
     * @param callback callback
     */
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

    /**
     * @brief Funcion para actualizar una ronda en la base de datos
     * @param round partida a añadir
     * @param callback callback
     */
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

    /**
     * @brief funcion para esperar cambios en la lista de partidas y actuaqlizarla
     * @param callback callback
     */
    fun startListeningChanges(callback: RoundRepository.RoundsCallback) {
        db = FirebaseDatabase.getInstance().getReference().child(DATABASENAME)
        db.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("DEBUG", p0.toString())
            }
            override fun onDataChange(p0: DataSnapshot) {
                var rounds = listOf<Round>()
                for (postSnapshot in p0.children)
                    if (isOpenOrIamIn(postSnapshot.getValue(Round::class.java)!!))
                        rounds += postSnapshot.getValue(Round::class.java)!!
                callback.onResponse(rounds)
            }
        })
    }

    /**
     * @brief funcion para esperar cambios en la partidas y actuaqlizarla
     * @param callback callback
     */
    fun startListeningBoardChanges(callback: RoundRepository.RoundsCallback){
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
}