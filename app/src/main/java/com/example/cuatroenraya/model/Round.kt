package com.example.cuatroenraya.model

import java.util.*
class Round(val stringTablero: String, val nombrePartida: String/*, val jugador1: String, val jugador2: String*/) {
    var id: String
    var title: String
    var date: String
    var board: TableroConecta4
    init {
        id = UUID.randomUUID().toString()
        title = nombrePartida
        date = Date().toString()
        board = TableroConecta4()
        board.stringToTablero(stringTablero)
    }
}
