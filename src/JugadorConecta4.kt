package es.uam.eps.multij
import TableroConecta4
import MovimientoConecta4

/**
 *
 * @author mfreire
 */
class JugadorConecta4
@JvmOverloads constructor(private val nombre: String) : Jugador {


    override fun onCambioEnPartida(evento: Evento) {
        when (evento.tipo) {
            Evento.EVENTO_FIN, Evento.EVENTO_CAMBIO -> {
                return
            }
            Evento.EVENTO_CONFIRMA -> {
                //TODO a lo mejor habría que pòner un finally en las exception para hacer algo siempre y que no se cuelgue
                try {
                    evento.partida.confirmaAccion(
                            this, evento.causa, Math.random() > .5)
                } catch (e: Exception) {
                    throw ExcepcionJuego("Error en la confirmación de la acción")
                }
            }
            Evento.EVENTO_TURNO -> {
                val t = evento.partida.tablero
                print("\n Elige columna ${evento.partida.getJugador(evento.partida.tablero.turno).nombre}: \n")
                val r = readLine()!!.toInt()
                val movimientoJugador = MovimientoConecta4(r)
                //TODO comprobare biewn el último movimiento
                try {
                    evento.partida.realizaAccion(AccionMover(this, movimientoJugador))
                } catch (e: Exception) {
                    throw ExcepcionJuego("Error en al elegir la columna")
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
