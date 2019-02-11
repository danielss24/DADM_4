import es.uam.eps.multij.Jugador
import es.uam.eps.multij.JugadorAleatorio
import es.uam.eps.multij.Partida
fun main(args: Array<String>) {
    val jugadores = arrayListOf<Jugador>()
    jugadores += JugadorAleatorio("Aleatorio")
    jugadores += JugadorEnRayaHumano("Humano")
    val partida = Partida(TableroCuatroEnRaya(), jugadores)
    partida.addObservador(ObservadorCuatroEnRaya())
    partida.comenzar()as

}