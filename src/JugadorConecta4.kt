package es.uam.eps.multij
import TableroConecta4
import MovimientoConecta4
import java.io.File
import java.io.FileNotFoundException

/**
 *
 * @author mfreire
 */
class JugadorConecta4
constructor(private val nombre: String) : Jugador {

    val OPCIONES_CORRECTAS = arrayListOf<Int>(0,1,2,3,4,5,6,8,9)

    override fun onCambioEnPartida(evento: Evento) {
        when (evento.tipo) {
            Evento.EVENTO_FIN, Evento.EVENTO_CAMBIO -> {
                return
            }
            Evento.EVENTO_CONFIRMA -> {
                try {
                    evento.partida.confirmaAccion(
                            this, evento.causa, Math.random() > .5)
                } catch (e: Exception) {
                    throw ExcepcionJuego("Error en la confirmación de la acción")
                }
            }
            Evento.EVENTO_TURNO -> {
                println("\n Introduzca un 8 para guardar partida, 9 para salir sin guardar.")
                println("\n Elige columna ${evento.partida.getJugador(evento.partida.tablero.turno).nombre}: ")
                var flagOpcionCorrecta = 0
                var opcion = 0

                while(flagOpcionCorrecta==0){
                    try {
                        opcion = readLine()!!.toInt()
                        if(opcion in OPCIONES_CORRECTAS){
                            flagOpcionCorrecta=1
                        }else{
                            println("<<$opcion>> no se encuentra entre las opciones correctas de ejecuión.")
                            println("Estado del tablero: ${evento.partida.tablero.tableroToString()}")
                            println("Introduzca un 8 para guardar partida, 9 para salir sin guardar.")
                            println("Elige columna " +
                                    "${evento.partida.getJugador(evento.partida.tablero.turno).nombre}: ")
                        }

                    }catch (e: java.lang.Exception){
                        println("La opción introducida no es correcta.")
                        println("Estado del tablero: ${evento.partida.tablero.tableroToString()}")
                        println("Introduzca un 8 para guardar partida, 9 para salir sin guardar.")
                        println("Elige columna " +
                                "${evento.partida.getJugador(evento.partida.tablero.turno).nombre}: ")
                    }
                }

                val movimientoJugador = MovimientoConecta4(opcion)

                try {
                    if (opcion == 8){
                        val path = File("./saves")
                        println("Estos son las partidas guardadas, elige el nombre para guardar esta partida\n" +
                                "Si ya existe, la partida se sobreecribirá.")
                        for (archivo in path.list()){
                            println("\t-> " + archivo)
                        }
                        print("\nNombre:")
                        val nombreFichero = readLine()!!.toString() + ".txt"
                        if (path.mkdirs()){
                            println("Se ha creado")
                        } else {
                            println("Ya esta creado")
                        }
                        val fichero = File(path, nombreFichero)
                        try {
                            var TurnoActual : String
                            if (this.nombre.equals(evento.partida.getJugador(0))){
                                TurnoActual = this.nombre
                            } else  {
                                TurnoActual = evento.partida.getJugador(0).nombre
                            }
                            var tableroTMP = evento.partida.tablero
                            if(tableroTMP is TableroConecta4){
                                var tableroArray = tableroTMP.imprimeTablero()
                                fichero.writeText("Turno: ${evento.partida.tablero.turno}\n" +
                                        "NumJugadas: ${evento.partida.tablero.numJugadas}\n" +
                                        "Mueve: " + TurnoActual + "\n" +
                                        "Jugador1: ${evento.partida.getJugador(0).nombre}\n" +
                                        "Jugador2: ${evento.partida.getJugador(1).nombre}\n" +
                                        "Tablero String: ${tableroArray}\n" +
                                        "Tablero Grafico: ${evento.partida.tablero}")
                            }
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        }
                        evento.partida.tablero.estado = Tablero.FINALIZADA
                    } else if (opcion == 9){
                        evento.partida.tablero.estado = Tablero.FINALIZADA
                        print("Hasta luego!")
                    } else {
                        evento.partida.realizaAccion(AccionMover(this, movimientoJugador))
                    }
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
