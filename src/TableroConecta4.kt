import es.uam.eps.multij.Movimiento
import es.uam.eps.multij.Tablero
import java.util.ArrayList

class TableroConecta4(var name: String): Tablero() {

    var tablero = ArrayList<ArrayList<Int>>()

    val NUM_COL = 7
    val NUM_FIL = 6
    val IS_EMPTY = -1

    fun Tablero(){
        TODO("tablero to cero")
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
        var MovimientosValidos = ArrayList<Movimiento>();
        for(col in 1..7){
            if (isFull(col) == false){
                MovimientosValidos += MovimientoConecta4(col=col)
            }
        }
        return MovimientosValidos
    }

    override fun mueve(m: Movimiento?) {
        if (m is MovimientoConecta4){
            if (esValido(m)==true){
                tablero[m.col][getFreePos(m.col)] = m.playerId
            } else {
                println("Error - Movimiento no valido")
            }
        }
    }

    override fun stringToTablero(cadena: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun tableroToString(): String {
        stringTablero
    }

    override fun toString(): String {
    }

    fun isFull(columna: Int): Boolean{
        if (tablero[columna][6]!=0){
            return true
        } else {
            return false
        }
    }

    fun getFreePos(columna: Int): Int{
        for (fil in 1..NUM_FIL){
            if (tablero[columna][fil]== IS_EMPTY ){
                return fil
            }
        }
        return -1
    }


}