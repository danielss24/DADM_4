package com.example.cuatroenraya.utility
import com.example.cuatroenraya.model.TableroConecta4

import java.util.*
class Round(val size: Int) {
    var id: String
    var title: String
    var date: String
    var board: TableroConecta4
    init {
        id = UUID.randomUUID().toString()
        title = "ROUND ${id.toString().substring(19, 23).toUpperCase()}"
        date = Date().toString()
        board = TableroConecta4()
    }
}
