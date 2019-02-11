/*
import es.uam.eps.multij.AccionMover
import es.uam.eps.multij.Evento
import es.uam.eps.multij.Jugador
import es.uam.eps.multij.Tablero
class JugadorTresEnRayaHumano(var name: String): Jugador {
    override fun getNombre() = name
    override fun puedeJugar(tablero: Tablero?) = true
    override fun onCambioEnPartida(evento: Evento?) {
        when (evento?.tipo) {
            Evento.EVENTO_TURNO -> {
                try {
                } catch (e: NumberFormatException) {
                    val turn = evento.partida.tablero.turno
                    Jugador.onCambioEnPartida(errorEvent)
                }
            }
            Evento.EVENTO_ERROR -> {
                println(evento.descripcion)
                evento.partida.continuar()
            }
        }
    }
}*/
