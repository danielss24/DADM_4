package com.example.cuatroenraya.model

import java.util.*
class Round() {
    var id: String
    var title: String
    var date: String
    var board: TableroConecta4
    init {
        id = UUID.randomUUID().toString()
        title =  UUID.randomUUID().toString()
        date = Date().toString()
        board = TableroConecta4()
    }
}
