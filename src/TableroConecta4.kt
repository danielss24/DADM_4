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
        for(col in 1..7){
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
        tableroString += " ---------------------\n"
        for (fil in (NUM_FIL-1) downTo 0){
            for (col in 0..(NUM_COL-1)){
                if (tablero[col][fil] == -1){
                    input = " "
                } else {
                    input = tablero[col][fil].toString()
                }
                tableroString += (" " + input)
            }
            tableroString += " \n"
        }
        tableroString += " ---------------------\n"
        tableroString += "  0  1  2  3  4  5  6 \n"
        return tableroString
    }

    override fun toString(): String {
        return tableroToString()
    }

    fun isFull(columna: Int): Boolean{
        //Lo he cambiaod de 0 a -1 porque 0 era jugador 1 y 1 jugadopr dos ahora
        //Also cambiado que aquí iban -1 porque contamos desde 0
        if (tablero[columna-1][NUM_FIL-1]!=-1){
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



}