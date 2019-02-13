import es.uam.eps.multij.Evento
import es.uam.eps.multij.PartidaListener
import es.uam.eps.multij.Tablero
import es.uam.eps.multij.Jugador

class ObservadorConecta4
@JvmOverloads constructor() : PartidaListener {

    var turno : Int = 1

    override fun onCambioEnPartida(evento: Evento){
        when(evento?.tipo){
            Evento.EVENTO_CAMBIO, Evento.EVENTO_FIN -> {
                //print("\n\nTurno: ${evento.partida.tablero.numJugadas} \n")
                //print("Mueve el jugador ${evento.partida.getJugador(evento.partida.tablero.turno).nombre} \n")
                print(evento.partida.tablero)
                print(evento.descripcion)
            }
            Evento.EVENTO_ERROR -> {
                print(evento.partida.tablero)
                print(evento.descripcion)
            }

        }
    }
}
