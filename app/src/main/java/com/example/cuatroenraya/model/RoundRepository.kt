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
    fun addRound(round: Round): Round {
        var i = 0
        for (roundF in rounds){
            if (roundF.nombrePartida == round.nombrePartida){
                roundF.board = round.board
                return roundF
            }
            i++
        }
        rounds.add(round)
        return round ?: throw Exception("Round not found.")
    }
}
