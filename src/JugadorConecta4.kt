package es.uam.eps.multij
import TableroConecta4

/**
 *
 * @author mfreire
 */
class JugadorConecta4
@JvmOverloads constructor(private val nombre: String) : Jugador {


    override fun onCambioEnPartida(evento: Evento) {
        when (evento.tipo) {
            Evento.EVENTO_FIN, Evento.EVENTO_CAMBIO -> {
                TODO("SEGUIR POR AQUI 12-02-2019")
            }
            Evento.EVENTO_CONFIRMA -> {
                // este jugador confirma al azar
                try {
                    evento.partida.confirmaAccion(
                            this, evento.causa, Math.random() > .5)
                } catch (e: Exception) {

                }
            }
            Evento.EVENTO_TURNO -> {
                // jugar al azar, que gran idea
                val t = evento.partida.tablero
                val r = (Math.random() * t.movimientosValidos().size).toInt()
                try {
                    evento.partida.realizaAccion(AccionMover(
                            this, t.movimientosValidos()[r]))
                } catch (e: Exception) {

                }

            }
        }
    }

    /**
     * Devuelve el nombre del jugador
     */
    override fun getNombre(): String {
        return nombre
    }

    /**
     * Este jugador juega *todos* los juegos
     */
    override fun puedeJugar(tablero: Tablero): Boolean {
        if (tablero is TableroConecta4){
            return true
        } else {
            return false
        }
    }

}
