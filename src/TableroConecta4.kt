import es.uam.eps.multij.Movimiento
import es.uam.eps.multij.Tablero
import MovimientoConecta4

class TableroConecta4(var name: String = "TableroConecta4"): Tablero() {

    var tablero = ArrayList<ArrayList<Int>>()

    val NUM_COL = 7
    val NUM_FIL = 6
    val IS_EMPTY = -1

    init {
        this.name = name
        this.turno = (0..1).random()
        this.estado = EN_CURSO
        //Está inicialización del tablero habría que mirarsela
        this.tablero = arrayListOf(arrayListOf(-1,-1,-1,-1,-1,-1), arrayListOf(-1,-1,-1,-1,-1,-1),
                arrayListOf(-1,-1,-1,-1,-1,-1), arrayListOf(-1,-1,-1,-1,-1,-1), arrayListOf(-1,-1,-1,-1,-1,-1),
                arrayListOf(-1,-1,-1,-1,-1,-1), arrayListOf(-1,-1,-1,-1,-1,-1))
    }

    override fun esValido(m: Movimiento?): Boolean {
        if (m is MovimientoConecta4) {
            if (isFull(m.col) == true) {
                return false
            } else {
                return true
            }
        }
        TODO("Exception de no es movimientoConecta4")
        return false
    }

    override fun movimientosValidos(): ArrayList<Movimiento> {
        var MovimientosValidos = ArrayList<Movimiento>()
        for(col in 0..6){
            if (isFull(col) == false){
                MovimientosValidos.add(MovimientoConecta4(col))
            }
        }
        return MovimientosValidos
    }

    override fun mueve(m: Movimiento?) {
        if (m is MovimientoConecta4){
            if (esValido(m)==true){
                tablero[m.col][getFreePos(m.col)] = this.turno//m.playerId
                this.cambiaTurno()
                this.ultimoMovimiento = m
                when (comprobacionConecta4()) {
                    EN_CURSO -> {

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

    override fun stringToTablero(cadena: String?) {
        if (cadena is String) {
            var filas = cadena.split("\n")
            var filaIndex = 0
            var columnaIndex = 0
            for (fil in filas){
                var pos = fil.split(" ")
                for (col in pos) {
                    tablero[columnaIndex][filaIndex] = col.toInt()
                    columnaIndex +=1
                }
                columnaIndex = 0
                filaIndex +=1
            }
        }
    }

    override fun tableroToString(): String {
        var tableroString = String()
        var input = " "
        tableroString += "\t--------------------------\n"
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

    override fun toString(): String {
        return tableroToString()
    }

    fun isFull(columna: Int): Boolean{
        //Lo he cambiaod de 0 a -1 porque 0 era jugador 1 y 1 jugadopr dos ahora
        //Also cambiado que aquí iban -1 porque contamos desde 0
        if (tablero[columna][NUM_FIL-1]!=-1){
            return true
        } else {
            return false
        }
    }

    fun getFreePos(columna: Int): Int{
        for (fil in 0..(NUM_FIL-1)){
            if (tablero[columna][fil]== IS_EMPTY ){
                return fil
            }
        }
        return -1
    }

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

    fun comprobacionConecta4_Horizontal(): Int {
        var col = 0
        var fil = 0
        while (col < NUM_COL - 3) {
            while (fil < NUM_FIL) {
                if (tablero[col][fil] == 1 && tablero[col][fil + 1] == 1 && tablero[col][fil + 2] == 1 && tablero[col][fil + 3] == 1) {
                    return FINALIZADA
                } else if (tablero[col][fil] == 0 && tablero[col][fil + 1] == 0 && tablero[col][fil + 2] == 0 && tablero[col][fil + 3] == 0) {
                    return FINALIZADA
                }
                fil++
            }
            col++
        }
        return EN_CURSO
    }

    fun comprobacionConecta4_Vertical(): Int {
        var col = 0
        var fil = 0
        while (fil < NUM_FIL) {
            while (col < NUM_COL) {
                if (tablero[col][fil] == 1 && tablero[col + 1][fil] == 1 && tablero[col + 2][fil] == 1 && tablero[col + 3][fil] == 1) {
                    return FINALIZADA
                } else if (tablero[col][fil] == 20 && tablero[col + 1][fil] == 0 && tablero[col + 2][fil] == 0 && tablero[col + 3][fil] == 0) {
                    return FINALIZADA
                }
                col++
            }
            fil++
        }
        return EN_CURSO
    }

    fun comprobacionConecta4_DiagonalSup(): Int {
        var col = 0
        var fil = 0
        while (col < NUM_COL - 3) {
            while (fil < NUM_FIL - 3) {
                if (tablero[col][fil] == 1 && tablero[col + 1][fil + 1] == 1 && tablero[col + 2][fil + 2] == 1 && tablero[col + 3][fil + 3] == 1) {
                    return FINALIZADA
                } else if (tablero[col][fil] == 0 && tablero[col + 1][fil + 1] == 0 && tablero[col + 2][fil + 2] == 0 && tablero[col + 3][fil + 3] == 0) {
                    return FINALIZADA
                }
                fil++
            }
            col++
        }
        return EN_CURSO
    }

    fun comprobacionConecta4_DiagonalInf(): Int {
        var col = 0
        while (col < NUM_COL - 3) {
            var fil = 5
            while (fil > NUM_FIL - 3) {
                if (tablero[col][fil] == 1 && tablero[col + 1][fil - 1] == 1 && tablero[col + 2][fil - 2] == 1 && tablero[col + 3][fil - 3] == 1) {
                    return FINALIZADA
                } else if (tablero[col][fil] == 0 && tablero[col + 1][fil - 1] == 0 && tablero[col + 2][fil - 2] == 0 && tablero[col + 3][fil - 3] == 0) {
                    return FINALIZADA
                }
                fil--
            }
            col++
        }
        return EN_CURSO
    }
}