
import es.uam.eps.multij.Jugador
import es.uam.eps.multij.JugadorAleatorio
import es.uam.eps.multij.JugadorConecta4
import es.uam.eps.multij.Partida
import TableroConecta4
fun main(args: Array<String>) {
    val jugadores = arrayListOf<Jugador>()
    jugadores += JugadorConecta4("Humano1")
    jugadores += JugadorConecta4("Humano2")
    val partida = Partida(TableroConecta4(), jugadores)
    partida.addObservador(ObservadorConecta4())
    partida.comenzar()

}