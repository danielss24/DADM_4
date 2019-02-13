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
                return
            }
            Evento.EVENTO_CONFIRMA -> {
                // este jugador confirma al azar
                //TODO a lo mejor habría que pòner un finally en las exception para hacer algo siempre y que no se cuelgue
                try {
                    evento.partida.confirmaAccion(
                            this, evento.causa, Math.random() > .5)
                } catch (e: Exception) {
                    //throw ExcepcionJuego("Error en la confirmación de la acción")
                }
            }
            Evento.EVENTO_TURNO -> {
                // jugar al azar, que gran idea
                val t = evento.partida.tablero
                print("Elige la columna:\n")
                val r = readLine()!!.toInt()
                try {
                    evento.partida.realizaAccion(AccionMover(this, t.movimientosValidos()[r]))
                } catch (e: Exception) {
                    //throw ExcepcionJuego("Error en el cambio de turno")
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
