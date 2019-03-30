package com.example.cuatroenraya.model

import com.example.cuatroenraya.R
import java.util.*
class Round() {
    var id: String
    var title: String
    var date: String
    var board: TableroConecta4
    init {
        id = UUID.randomUUID().toString()
        var titleTMP = UUID.randomUUID().toString()
        title = "GAME-" + titleTMP.substring((titleTMP.length)-4)
        date = Date().toString()
        board = TableroConecta4()
    }
}
