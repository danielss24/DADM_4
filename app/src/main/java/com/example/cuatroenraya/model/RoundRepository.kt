package com.example.cuatroenraya.model

import es.uam.eps.multij.Jugador
import es.uam.eps.multij.JugadorAleatorio
import java.io.File

/**
 * @brief objeto de lista de partidas
 */
object RoundRepository {
    val rounds = ArrayList<Round>()

    init {
    }

    /**
     * @brief funcion que devuelve partidas
     * @param id id de partida a devolver
     * @return devuelve la partida o error
     */
    fun getRound(id: String): Round {
        val round = rounds.find { it.id == id }
        return round ?: throw Exception("Round not found.")
    }

    /**
     * @brief funcion que añade partidas
     * @return id de partida añadida
     */
    fun addRound():String{
        var round = Round()
        rounds.add(round)
        return round.id
    }

    /**
     * @brief guarda partida
     * @param round partida a guardar
     */
    fun saveRound(round: Round){
        rounds.add(round)
    }
}
