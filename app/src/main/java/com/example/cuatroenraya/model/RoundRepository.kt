package com.example.cuatroenraya.model

import es.uam.eps.multij.Jugador
import es.uam.eps.multij.JugadorAleatorio
import java.io.File

object RoundRepository {
    val rounds = ArrayList<Round>()
    init {
    }
    fun getRound(id: String): Round {
        val round = rounds.find { it.id == id }
        return round ?: throw Exception("Round not found.")
    }
    fun addRound():String{
        var round = Round()
        rounds.add(round)
        return round.id
    }

    fun saveRound(round: Round){
        rounds.add(round)
    }
}
