package com.example.cuatroenraya.model

import es.uam.eps.multij.Movimiento
import es.uam.eps.multij.Tablero
import java.io.File

/**
 * @brief Clase de tablero conecta 4
 * @param nombre tablero con valor predefinido
 */
class TableroConecta4(var name: String = "com.example.cuatroenraya.model.TableroConecta4"): Tablero() {

    val NUM_COL = 7
    val NUM_FIL = 6
    val IS_EMPTY = -1

    val JUGADOR1 = 1

	var tablero = Array(NUM_COL) { arrayOfNulls<Int>(NUM_FIL) }

    init {
        this.name = name
        this.turno = 0
        this.estado = EN_CURSO
		for (col in 0..(NUM_COL-1)){
            for(fil in 0 ..(NUM_FIL-1)){
                tablero[col][fil] = IS_EMPTY
            }
        }
    }

    /**
     * @brief funcion validadora de movimientos
     * @param m movimiento a confirmar
     */
    override fun esValido(m: Movimiento?): Boolean {
        if (m is MovimientoConecta4) {
            if (isFull(m.col) == true) {
                return false
            } else {
                return true
            }
        }
        return false
    }

    /**
     * @brief devuelve lista de movimientos validos
     * @return lista de movimientos validos
     */
    override fun movimientosValidos(): ArrayList<Movimiento> {
        var MovimientosValidos = ArrayList<Movimiento>()
        for (col in 0..6) {
            if (isFull(col) == false){
                MovimientosValidos.add(MovimientoConecta4(col))
            }
        }
        return MovimientosValidos
    }

    /**
     * @brief funcion que mueve ficha comprobando si es valido
     * @param m movimiento a realizar
     */
    override fun mueve(m: Movimiento?) {
        if (m is MovimientoConecta4){

            if (esValido(m)==true){
                tablero[m.col][getFreePos(m.col)] = this.turno//m.playerId
                when (comprobacionConecta4()) {
                    EN_CURSO -> {
                        this.cambiaTurno()
                        this.ultimoMovimiento = m
                    }
                    FINALIZADA -> {

                    }
                    TABLAS -> {

                    }
                }
            } else {
                println("Error - Movimiento no valido")
            }
        }
    }

    /**
     * @brief funcion comprobadora de si columna esta llena
     * @param columna columna a comprobar
     * @return false o true si esta llena o no
     */
    fun isFull(columna: Int): Boolean{
        if (this.tablero[columna][NUM_FIL - 1] != -1) {
            return true
        } else {
            return false
        }
    }

    /**
     * @brief devuelve la fila libre donde insertar la ficha
     * @param columna columna a comprobar
     * @return fila a introducir o -1 si no hay sitio
     */
    fun getFreePos(columna: Int): Int{
        for (fil in 0..(NUM_FIL-1)){
            if (tablero[columna][fil]== IS_EMPTY ){
                return fil
            }
        }
        return -1
    }

    /**
     * @brief funcion que comprueba todas las posibles formas de ganar
     * @return devuelve estado de la partida
     */
    fun comprobacionConecta4(): Int {
        this.estado = EN_CURSO
        if (FINALIZADA == comprobacionConecta4_Horizontal()) {
            this.estado = FINALIZADA
        } else if (FINALIZADA == comprobacionConecta4_Vertical()) {
            this.estado = FINALIZADA
        } else if (FINALIZADA == comprobacionConecta4_DiagonalSup()) {
            this.estado = FINALIZADA
        } else if (FINALIZADA == comprobacionConecta4_DiagonalInf()) {
            this.estado = FINALIZADA
        } else if (this.movimientosValidos().size == 0) {
            this.estado = TABLAS
        }
        return this.estado
    }

    /**
     * @brief funcion comprobadora de lineas horizontales
     * @return devuelve estado de partida
     */
    fun comprobacionConecta4_Horizontal(): Int {
        var col = 0
        var fil = 0
        while (fil < NUM_FIL ) {
            col = 0
            while (col < NUM_COL - 3) {
                if (tablero[col][fil] == 1 && tablero[col+1][fil] == 1
                        && tablero[col+2][fil] == 1 && tablero[col+3][fil] == 1) {
                    return FINALIZADA
                } else if (tablero[col][fil] == 0 && tablero[col+1][fil] == 0
                        && tablero[col+2][fil] == 0 && tablero[col+3][fil] == 0) {
                    return FINALIZADA
                }
                col++
            }
            fil++
        }
        return EN_CURSO
    }

    /**
     * @brief funcion comprobadora de lineas verticales
     * @return devuelve estado de partida
     */
    fun comprobacionConecta4_Vertical(): Int {
        var col = 0
        var fil = 0
        while (col < NUM_COL) {
            fil = 0
            while (fil < NUM_FIL-3) {
                if (tablero[col][fil] == 1 && tablero[col][fil+1] == 1 && tablero[col][fil+2] == 1
                        && tablero[col][fil+3] == 1) {
                    return FINALIZADA
                } else if (tablero[col][fil] == 0 && tablero[col][fil+1] == 0 && tablero[col][fil+2] == 0
                        && tablero[col][fil+3] == 0) {
                    return FINALIZADA
                }
                fil++
            }
            col++
        }
        return EN_CURSO
    }

    /**
     * @brief funcion comprobadora de diagonal superior
     * @return devuelve estado de partida
     */
    fun comprobacionConecta4_DiagonalSup(): Int {
        var colRecorrer = 0
        var colComprobacion = 0
        var fil = 0
        while (colRecorrer < NUM_COL - 3) {
            colComprobacion = colRecorrer
            fil = 0
            while (fil < NUM_FIL - 3) {
                if (tablero[colComprobacion][fil] == 1 && tablero[colComprobacion + 1][fil + 1] == 1 &&
                        tablero[colComprobacion + 2][fil + 2] == 1 && tablero[colComprobacion + 3][fil + 3] == 1) {
                    return FINALIZADA
                } else if (tablero[colComprobacion][fil] == 0 && tablero[colComprobacion + 1][fil + 1] == 0
                        && tablero[colComprobacion + 2][fil + 2] == 0 && tablero[colComprobacion + 3][fil + 3] == 0) {
                    return FINALIZADA
                }
                fil++
                colComprobacion++
            }
            colRecorrer++
        }
        return EN_CURSO
    }

    /**
     * @brief funcion comprobadora de diagonal inferior
     * @return devuelve estado de la partida
     */
    fun comprobacionConecta4_DiagonalInf(): Int {
        var col= 0
        var fil = 5
        while (col < NUM_COL - 3) {
            fil = 5
            while (fil > 2) {
                if (tablero[col][fil] == 1 && tablero[col + 1][fil -1 ] == 1 &&
                        tablero[col + 2][fil - 2] == 1 && tablero[col + 3][fil - 3] == 1) {
                    return FINALIZADA
                } else if (tablero[col][fil] == 0 && tablero[col + 1][fil - 1] == 0
                        && tablero[col + 2][fil - 2] == 0 && tablero[col + 3][fil - 3] == 0) {
                    return FINALIZADA
                }
                fil--
            }
            col++
        }
        return EN_CURSO
    }

    /**
     * @brief devuelve estado del tablero en una posicion exacta
     * @param fila fila de tablero
     * @param columna columna de tablero
     * @return valor de la posicion
     */
    fun getTablero(fila: Int, columna: Int):Int?{
        return tablero[columna][fila]
    }

    /**
     * @brief convierte tablero a string
     * @return tableroString
     */
    override fun tableroToString(): String {
        var col = NUM_COL
        var tableroString = ""

        while (col>0) {
            var fil = NUM_FIL
            while (fil > 0) {
                if (tablero[col-1][fil-1] == -1) {
                    tableroString += "X"
                } else {
                    tableroString += tablero[col-1][fil-1].toString()
                }
                fil--
            }
            col--
        }
        return tableroString
    }

    /**
     * @brief funcion to string de tablero
     * @return tablero string
     */
    override fun toString(): String {
        return tableroToString()
    }

    /**
     * @brief impresion de tablero grafico
     * @return cadena de tablero grafico
     */
    fun imprimeTablero(): String {
        var tableroString = String()
        var input = " "
        tableroString += "\n\t--------------------------\n"
        for (fil in (NUM_FIL-1) downTo 0){
            for (col in 0..(NUM_COL-1)){
                if (tablero[col][fil] == -1){
                    input = " "
                } else {
                    input = tablero[col][fil].toString()
                }
                tableroString += ("\t" + input)
            }
            tableroString += " \n"
        }
        tableroString += "\t--------------------------\n"
        tableroString += "\t0\t1\t2\t3\t4\t5\t6 \n"
        return tableroString
    }

    /**
     * @brief guarda el tablero en un fichero
     * @param fichero fichero donde guardar
     * @param jugador1 jugador1 de la partida
     * @param jugador2 jugador2 de la partida
     * @param jugadorActivo jugador que tiene el turno
     */
    fun guardaTablero(fichero: File, jugador1: String, jugador2: String, jugadorActivo: String){
        var tableroArray = this.tableroToString()
        fichero.writeText("Turno: ${this.turno}\n" +
                "NumJugadas: ${this.numJugadas}\n" +
                "Mueve: " + jugadorActivo + "\n" +
                "Jugador1: ${jugador1}\n" +
                "Jugador2: ${jugador2}\n" +
                "Tablero String: ${tableroArray}\n" +
                "Tablero Grafico: ${this.imprimeTablero()}")
    }

    /**
     * @brief convierte string a tablero objeto
     * @param cadena cadena a convertir
     */
    override fun stringToTablero(cadena: String?) {
        if(cadena is String){
            cadena!!.toList()
            var iterator = 0
            var col = NUM_COL
            while (col > 0 ){
                var fil = NUM_FIL
                while (fil > 0){
                    if(cadena[iterator] == '1'){
                        tablero[col-1][fil-1] = 1
                    }else if(cadena[iterator] == '0'){
                        tablero[col-1][fil-1] = 0
                    }else{
                        tablero[col-1][fil-1] = -1
                    }
                    iterator++
                    fil--
                }
                col--
            }
        }
    }

    /**
     * @brief funcion cargadora de tableros por string
     * @param cadena cadena a convertir
     */
    fun cargaTablero(cadena: String?){
        if(cadena is String) {
            var iteradorFichero = cadena.split("\n")

            //seteando turno
            var turnoActual = iteradorFichero[0].split(":")[1].trim()
            turno = turnoActual.toInt()

            //seteando Num jugadas
            numJugadas = iteradorFichero[1].split(":")[1].trim().toInt()

            //seteando tablero
            this.stringToTablero(iteradorFichero[5].split(":")[1].trim())
        }
    }
}