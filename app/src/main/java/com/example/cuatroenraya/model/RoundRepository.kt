package com.example.cuatroenraya.model

object RoundRepository {
    val rounds = ArrayList<Round>()
    init {
        for (i in 0..100)
            rounds.add(Round(3))
    }
    fun getRound(id: String): Round {
        val round = rounds.find { it.id == id }
        return round ?: throw Exception("Round not found.")
    }
}
