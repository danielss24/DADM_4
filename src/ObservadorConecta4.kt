import es.uam.eps.multij.Evento
import es.uam.eps.multij.PartidaListener
import es.uam.eps.multij.Jugador

class ObservadorConecta4
@JvmOverloads constructor() : PartidaListener {

    override fun onCambioEnPartida(evento: Evento){
        when(evento?.tipo){
            Evento.EVENTO_CAMBIO -> {

            }
            Evento.EVENTO_ERROR -> {
                println(evento.descripcion)
                evento.partida.continuar()
            }
        }
    }
}
