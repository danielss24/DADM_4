import es.uam.eps.multij.Movimiento

public class MovimientoConecta4 () : Movimiento() {

    var col : Int = -1
    var playerId : Int = -1

    fun MovimientoConecta4(col : Int, playerId: Int = -1){
        this.col = col
        this.playerId = playerId
    }



    /**
     * genera una cadena que describe este movimiento
     * @return una cadena de la forma "E2-E4", o en caso de coronacion, "E7-E8=r"
     */
    override fun toString(): String {
        return playerId.toString() + "|" + col.toString()
    }

    /** compara esta jugada con otra, a fin de comprobar si son iguales
     * @param o otro Movimiento
     * @return el valor de la comparacion (true o false)
     */
    override fun equals(o: Any?): Boolean {
        if (o is MovimientoConecta4) {
            if (o.col == this.col && o.playerId == this.playerId) {
                return true
            } else {
                return false
            }
        }
        return false
    }



}
